package shoppingmall.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.MarketPayDtoV2;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.pay.KakaoPayApprovalVO;
import shoppingmall.project.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final MarketService marketService;
    private final DeliveryService deliveryService;
    private final ItemService itemService;
    private final PurchaseService purchaseService;


    @PostMapping("/ready")
    public String kakaoPay(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return "redirect:" + kakaoService.getReadyParameters(user.getId());
    }
    @GetMapping("/buyItem")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model,HttpSession session) {
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        KakaoPayApprovalVO kakaoPayApprovalVO = kakaoService.kakaoPayInfo(pg_token, user.getId());
        int totalPrice = kakaoPayApprovalVO.getAmount().getTotal();

        int newTotalPrice = userService.addAccumulatedAmount(user, totalPrice);
        // 장바구니의 아이템 id find하고 수량만 set 열어서 사용
        List<MarketPayDtoV2> shoppingBasket = marketService.purchaseItem(user.getId());

        //구매한 배송 정보 저장
        Delivery delivery = deliveryService.addDelivery(user);

        for (MarketPayDtoV2 marketPayDtoV2 : shoppingBasket) {
            ItemDto item = new ItemDto(
                    marketPayDtoV2.getId(),
                    marketPayDtoV2.getName(),
                    marketPayDtoV2.getPrice(),
                    marketPayDtoV2.getOrderQuantity()
            );
            Item findById = itemService.findById(item.getId());
            purchaseService.addPurchase(item,delivery,user);
            findById.purchaseAfterQuantity(marketPayDtoV2.getOrderQuantity());
        }

        marketService.deleteMarketUser(user.getId());



        model.addAttribute("info",kakaoPayApprovalVO);
        return "order/buyItem";
    }

    @GetMapping("/cancel")
    public String kakaoPayCancel() {
        return "order/cancel";
    }
    @GetMapping("/fail")
    public String kakaoPayFail() {
        return "order/fail";
    }
}
