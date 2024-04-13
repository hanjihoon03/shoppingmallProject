package shoppingmall.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.*;
import shoppingmall.project.domain.item.Item;

import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.UserRepository;
import shoppingmall.project.service.DeliveryService;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;
import shoppingmall.project.service.UserService;

import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;
    private final ItemService itemService;
    private final UserService userService;
    private final DeliveryService deliveryService;


    @GetMapping("/purchase")
    public String purchase(Model model, HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        List<ItemDto> itemDtos = marketService.purchaseItem(session);
        model.addAttribute("items", itemDtos);

        int totalPrice = marketService.purchaseTotalPrice(itemDtos, user);
        model.addAttribute("totalPrice", totalPrice);

        Tier userTier = userService.findUserTier(user.getId());


        int discountAmount = marketService.discountAmount(totalPrice, userTier);
        model.addAttribute("discount", discountAmount);

        return "/order/purchase";
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
    public String buyItem(HttpSession session, @RequestParam(required = false) Integer totalPrice) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        if (totalPrice != null) {
            int newTotalPrice = userService.addAccumulatedAmount(user, totalPrice);
            session.setAttribute("totalPrice", newTotalPrice);
        } else {
            // totalPrice가 없는 경우 예외 처리
        }

        // 장바구니의 아이템 id find하고 수량만 set 열어서 사용
        List<ItemDto> itemDtos = marketService.purchaseItem(session);


        for (ItemDto itemDto : itemDtos) {
            Item buyItem = itemService.findById(itemDto.getId());

            ItemDto item = new ItemDto(
                    itemDto.getId(),
                    itemDto.getName(),
                    itemDto.getPrice(),
                    itemDto.getQuantity()
            );
            //딜리버리에 아이템 넣기
            deliveryService.addDelivery(item, user);

            buyItem.purchaseAfterQuantity(itemDto.getQuantity());
        }

        marketService.deleteMarketUser(user.getId());


        // 영속성 컨텍스트에 올라가니까 올려서 수량 바꾸고 플러시
        return "order/buyItem";
    }


}
