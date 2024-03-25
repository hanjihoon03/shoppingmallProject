package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.service.ItemService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("book")
    public String createBook(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemBook";
    }

    @PostMapping("book")
    public String saveBook(BookForm bookForm) throws IOException {
        itemService.saveBook(bookForm);
        return "redirect:";
    }

    @GetMapping("clothes")
    public String createClothes(Model model) {
        model.addAttribute("clothesForm", new ClothesForm());
        return "items/createItemClothes";
    }

    @PostMapping("clothes")
    public String saveClothes(ClothesForm clothesForm) throws IOException {
        itemService.saveClothes(clothesForm);
        return "redirect:";
    }

    @GetMapping("food")
    public String createFood(Model model) {
        model.addAttribute("foodForm", new FoodForm());
        return "items/createItemFood";
    }

    @PostMapping("food")
    public String saveFood(FoodForm foodForm) throws IOException {
        itemService.saveFood(foodForm);
        return "redirect:";
    }

    @GetMapping("electronics")
    public String createElectronics(Model model) {
        model.addAttribute("electronicsForm", new ElectronicsForm());
        return "items/createItemElectronics";
    }

    @PostMapping("electronics")
    public String saveElectronics(ElectronicsForm electronicsForm) throws IOException {
        itemService.saveElectronics(electronicsForm);
        return "redirect:";
    }

    @GetMapping("/listBook")
    public String bookList(Model model) {
        List<BookAndFileDto> allBook = itemService.findAllBook();

        model.addAttribute("allBook", allBook);

        return "/list/bookList";
    }
    @GetMapping("/listClothes")
    public String clothesList(Model model) {
        List<ClothesAndFileDto> allClothes = itemService.findAllClothes();

        model.addAttribute("allClothes", allClothes);

        return "/list/clothesList";
    }
    @GetMapping("/listElectronics")
    public String electronicsList(Model model) {
        List<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics();

        model.addAttribute("allElectronics", allElectronics);

        return "/list/electronicsList";
    }
    @GetMapping("/listFood")
    public String foodList(Model model) {
        List<FoodAndFileDto> allFood = itemService.findAllFood();

        model.addAttribute("allFood", allFood);

        return "/list/foodList";
    }



    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // 파일 경로를 이용하여 실제 파일의 위치를 확인
        String fullPath = itemService.getFullPath(filename);
        Path filePath = Paths.get(fullPath);

        // 파일이 존재하고, 허용된 디렉토리에 있는지 확인
        if (Files.exists(filePath) && filePath.startsWith(Paths.get(fileDir))) {
            // UrlResource를 사용하여 리소스를 반환
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 이미지인 경우에는 적절한 미디어 타입을 설정
                    .body(resource).getBody();
        } else {
            // 파일이 존재하지 않거나 허용되지 않은 디렉토리에 있는 경우 404 에러 반환
            return (Resource) ResponseEntity.notFound().build();
        }
    }
}
