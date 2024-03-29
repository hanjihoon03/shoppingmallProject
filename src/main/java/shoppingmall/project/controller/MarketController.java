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
import shoppingmall.project.domain.item.Item;

import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;
    private final ItemService itemService;


    @GetMapping("/purchase")
    public String purchase(Model model, HttpSession session) {


        List<ItemDto> itemDtos = marketService.purchaseItem(session);
        model.addAttribute("items", itemDtos);
        int totalPrice = marketService.purchaseTotalPrice(itemDtos);
        model.addAttribute("totalPrice", totalPrice);

        return "/order/purchase";
    }
    @GetMapping("/purchase/delete/{id}")
    public String delete(@PathVariable Long id) {
        // 삭제 작업 수행
        marketService.deleteMarketItem(id);
        // 삭제 후 목록 페이지로 redirect
        return "redirect:/purchase";
    }

    @GetMapping("/buy")
    public String buyItem(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        // 장바구니의 아이템 id find하고 수량만 set 열어서 사용
        List<ItemDto> itemDtos = marketService.purchaseItem(session);
        for (ItemDto itemDto : itemDtos) {
            Item buyItem = itemService.findById(itemDto.getId());
            buyItem.purchaseAfterQuantity(itemDto.getQuantity());
        }
        marketService.deleteMarketUser(user.getId());

        // 영속성 컨텍스트에 올라가니까 올려서 수량 바꾸고 플러시
        return "order/buyItem";
    }


}
