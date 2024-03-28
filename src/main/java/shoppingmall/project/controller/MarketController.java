package shoppingmall.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.dto.*;
import shoppingmall.project.repository.MarketRepository;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;



//    @GetMapping("cart")
//    public String shoppingCart(Model model, HttpSession session) {
//
//        Map<Long, Integer> allMarketItemQuantity = marketService.findAllMarketItemQuantity();
//
//        List<Long> itemIds = allMarketItemQuantity.keySet().stream().collect(Collectors.toList());
//        List<Integer> quantity = allMarketItemQuantity.values().stream().collect(Collectors.toList());
//
//        model.addAttribute("itemIds", itemIds);
//        model.addAttribute("quantity", quantity);
//
//        List<FoodAndFileDto> allFood = itemService.findAllFood();
//
//        model.addAttribute("allFood", allFood);
//
//        List<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics();
//
//        model.addAttribute("allElectronics", allElectronics);
//
//        List<ClothesAndFileDto> allClothes = itemService.findAllClothes();
//
//        model.addAttribute("allClothes", allClothes);
//
//        List<BookAndFileDto> allBook = itemService.findAllBook();
//
//        model.addAttribute("allBook", allBook);
//
//
//
//        return "/order/shoppingCart";
//    }

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
        marketService.deleteMarket(id);
        // 삭제 후 목록 페이지로 redirect
        return "redirect:/purchase";
    }
    @PostMapping("/buy")
    public String buyItem(HttpServletRequest request) {
        //장바구니의 아이템 id find하고 수량만 set 열어서 사용
        //영속성컨텍스트에 올라가니까 올려서 수량 바꾸고 플러시


        return "/buyItem";
    }
}
