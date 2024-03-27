package shoppingmall.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.repository.MarketRepository;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;
    private final ItemService itemService;


    @GetMapping("cart")
    public String shoppingCart(Model model) {

        Map<Long, Integer> allMarketItemQuantity = marketService.findAllMarketItemQuantity();

        List<Long> itemIds = allMarketItemQuantity.keySet().stream().collect(Collectors.toList());
        List<Integer> quantity = allMarketItemQuantity.values().stream().collect(Collectors.toList());

        model.addAttribute("itemIds", itemIds);
        model.addAttribute("quantity", quantity);

        List<FoodAndFileDto> allFood = itemService.findAllFood();

        model.addAttribute("allFood", allFood);

        List<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics();

        model.addAttribute("allElectronics", allElectronics);

        List<ClothesAndFileDto> allClothes = itemService.findAllClothes();

        model.addAttribute("allClothes", allClothes);

        List<BookAndFileDto> allBook = itemService.findAllBook();

        model.addAttribute("allBook", allBook);



        return "/order/shoppingCart";
    }

    @PostMapping("/buy")
    public String purchase(HttpSession session) {

        session.getAttribute()
    }
}
