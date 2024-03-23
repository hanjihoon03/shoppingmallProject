package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.service.ItemService;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("book")
    public String createBook(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemBook";
    }

    @PostMapping("book")
    public String saveBook(BookForm bookForm) {
        itemService.saveBook(bookForm);
        return "redirect:";
    }

    @GetMapping("clothes")
    public String createClothes(Model model) {
        model.addAttribute("clothesForm", new ClothesForm());
        return "items/createItemClothes";
    }

    @PostMapping("clothes")
    public String saveClothes(ClothesForm clothesForm) {
        itemService.saveClothes(clothesForm);
        return "redirect:";
    }

    @GetMapping("food")
    public String createFood(Model model) {
        model.addAttribute("foodForm", new FoodForm());
        return "items/createItemFood";
    }

    @PostMapping("food")
    public String saveFood(FoodForm foodForm) {
        itemService.saveFood(foodForm);
        return "redirect:";
    }

    @GetMapping("electronics")
    public String createElectronics(Model model) {
        model.addAttribute("electronicsForm", new ElectronicsForm());
        return "items/createItemElectronics";
    }

    @PostMapping("electronics")
    public String saveElectronics(ElectronicsForm electronicsForm) {
        itemService.saveElectronics(electronicsForm);
        return "redirect:";
    }

}
