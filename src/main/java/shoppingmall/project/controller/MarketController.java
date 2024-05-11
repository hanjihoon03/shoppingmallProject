package shoppingmall.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.*;

import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.service.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;
    private final UserService userService;


    @GetMapping("/purchase")
    public String purchase(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        List<MarketPayDtoV2> shoppingBasket = marketService.purchaseItem(user.getId());
        model.addAttribute("items", shoppingBasket);

        int totalPrice = marketService.purchaseTotalPrice(shoppingBasket, user);
        model.addAttribute("totalPrice", totalPrice);
        Tier userTier = userService.findUserTier(user.getId());

        int discountAmount = marketService.discountAmount(totalPrice, userTier);
        model.addAttribute("discount", discountAmount);

        return "order/purchase";
    }


    @GetMapping("/purchaseV2")
    public String purchaseV2(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        List<ItemDto> itemDtos = marketService.purchaseItemV2(session);
        model.addAttribute("items", itemDtos);

        int totalPrice = marketService.purchaseTotalPriceV2(itemDtos, user);
        model.addAttribute("totalPrice", totalPrice);

        Tier userTier = userService.findUserTier(user.getId());


        int discountAmount = marketService.discountAmount(totalPrice, userTier);
        model.addAttribute("discount", discountAmount);

        return "order/purchaseV2";
    }






    @GetMapping("/purchase/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        // 삭제 작업 수행
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        marketService.deleteMarketItem(id, user.getId());

        // 삭제 후 목록 페이지로 redirect
        return "redirect:/purchase";
    }

    @GetMapping("/buy")
    public String buyItem() {

        return "order/kakaopay";
    }


}
