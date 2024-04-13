package shoppingmall.project.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.service.FileService;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final MarketService marketService;
    private final FileService fileService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("book")
    public String createBook(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemBook";
    }

    @PostMapping("book")
    public String saveBook(@ModelAttribute BookForm bookForm){
        try {
            itemService.saveBook(bookForm);
            return "redirect:";
        } catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }

    }

    @GetMapping("clothes")
    public String createClothes(Model model) {
        model.addAttribute("clothesForm", new ClothesForm());
        return "items/createItemClothes";
    }

    @PostMapping("clothes")
    public String saveClothes(@ModelAttribute ClothesForm clothesForm) {
        try {
        itemService.saveClothes(clothesForm);
        return "redirect:";
        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("food")
    public String createFood(Model model) {
        model.addAttribute("foodForm", new FoodForm());
        return "items/createItemFood";
    }

    @PostMapping("food")
    public String saveFood(@ModelAttribute FoodForm foodForm){
        try {
        itemService.saveFood(foodForm);
        return "redirect:";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("electronics")
    public String createElectronics(Model model) {
        model.addAttribute("electronicsForm", new ElectronicsForm());
        return "items/createItemElectronics";
    }

    @PostMapping("electronics")
    public String saveElectronics(@ModelAttribute ElectronicsForm electronicsForm){
        try {
        itemService.saveElectronics(electronicsForm);
        return "redirect:";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("/bookList")
    public String bookList(Model model) {
        List<BookAndFileDto> allBook = itemService.findAllBook();

        model.addAttribute("allBook", allBook);

        return "/list/bookList";
    }
    @GetMapping("/clothesList")
    public String clothesList(Model model) {
        List<ClothesAndFileDto> allClothes = itemService.findAllClothes();

        model.addAttribute("allClothes", allClothes);

        return "/list/clothesList";
    }
    @GetMapping("/electronicsList")
    public String electronicsList(Model model) {
        List<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics();

        model.addAttribute("allElectronics", allElectronics);

        return "/list/electronicsList";
    }
    @GetMapping("/foodList")
    public String foodList(Model model) {
        List<FoodAndFileDto> allFood = itemService.findAllFood();

        model.addAttribute("allFood", allFood);

        return "/list/foodList";
    }
    @PostMapping("/addCart")
    public String buyItem(@RequestParam("itemId") Long itemId,
                        @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                          HttpSession session) {

        // 상품 아이디와 수량 세션에 저장
        // 장바구니추가시마다 정보 누적 생성
        log.info("itemId={}", itemId);
        Item cartAddItem = itemService.findById(itemId);

        if (quantity > cartAddItem.getQuantity()) {
            return "error/cartError";
        }


        marketService.addToCart(itemId, quantity, session, cartAddItem);
        log.info("inCart={}, {}",itemId, quantity);
        log.info("item={}", cartAddItem.getName());

        return "redirect:";


    }

    @GetMapping("/modifyBook")
    public String itemBookList(Model model) {
        //수정을 위한 현재 아이템 표시
        List<BookAndFileDto> allBook = itemService.findAllBook();

        model.addAttribute("allBook", allBook);

        return "/admin/modifyBook";
    }

    @GetMapping("modifyBook/{itemId}/edit")
    public String updateItemBook(@PathVariable("itemId") Long itemId, Model model) {
        Book oneBook = itemService.findOneBook(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        BookAndFileDto bookAndFileDto = new BookAndFileDto(
                oneBook.getId(),
                oneBook.getIsbn(),
                oneBook.getAuthor(),
                oneBook.getName(),
                oneBook.getPrice(),
                oneBook.getQuantity(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );
        model.addAttribute("bookAndFileDto", bookAndFileDto);


        return "admin/updateItemBook";

    }


    @PostMapping("/modifyBook/{itemId}/edit")
    public String updateBook(@PathVariable Long itemId, @ModelAttribute("form") BookForm bookForm){
        try {
        itemService.updateBook(itemId, bookForm);
        return "redirect:/modifyBook";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("/modifyClothes")
    public String itemClothesList(Model model) {
        //수정을 위한 현재 아이템 표시
        List<ClothesAndFileDto> allClothes = itemService.findAllClothes();

        model.addAttribute("allClothes", allClothes);

        return "/admin/modifyClothes";
    }

    @GetMapping("modifyClothes/{itemId}/edit")
    public String updateItemClothes(@PathVariable("itemId") Long itemId, Model model) {

        Clothes oneClothes = itemService.findOneClothes(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);
        //객체 생성 서비스로 빼기
        ClothesAndFileDto clothesAndFileDto = new ClothesAndFileDto(
                oneClothes.getId(),
                oneClothes.getName(),
                oneClothes.getPrice(),
                oneClothes.getQuantity(),
                oneUploadFile.getStoreFileName(),
                oneUploadFile.getUploadFileName(),
                oneClothes.getClothesType(),
                oneClothes.getBrand(),
                oneClothes.getSize()
        );
        model.addAttribute("clothesAndFileDto", clothesAndFileDto);


        return "admin/updateItemClothes";

    }


    @PostMapping("/modifyClothes/{itemId}/edit")
    public String updateClothes(@PathVariable Long itemId, @ModelAttribute("form") ClothesForm clothesForm){
        try {
        itemService.updateClothes(itemId, clothesForm);
        return "redirect:/modifyClothes";
        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }


    @GetMapping("/modifyElectronics")
    public String itemElectronicsList(Model model) {
        //수정을 위한 현재 아이템 표시
        List<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics();

        model.addAttribute("allElectronics", allElectronics);
        return "/admin/modifyElectronics";
    }


    @GetMapping("modifyElectronics/{itemId}/edit")
    public String updateItemElectronics(@PathVariable("itemId") Long itemId, Model model) {
        Electronics oneElectronics = itemService.findOneElectronics(itemId);

        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        ElectronicsAndFileDto electronicsAndFileDto = new ElectronicsAndFileDto(
                oneElectronics.getId(),
                oneElectronics.getName(),
                oneElectronics.getPrice(),
                oneElectronics.getQuantity(),
                oneElectronics.getBrand(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );
        model.addAttribute("electronicsAndFileDto", electronicsAndFileDto);


        return "admin/updateItemElectronics";

    }


    @PostMapping("/modifyElectronics/{itemId}/edit")
    public String updateElectronics(@PathVariable Long itemId, @ModelAttribute("form") ElectronicsForm electronicsForm){
        try {
        itemService.updateElectronics(itemId,electronicsForm);
        return "redirect:/modifyElectronics";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }


    @GetMapping("/modifyFood")
    public String itemFoodList(Model model) {
        //수정을 위한 현재 아이템 표시
        List<FoodAndFileDto> allFood = itemService.findAllFood();

        model.addAttribute("allFood", allFood);

        return "/admin/modifyFood";
    }


    @GetMapping("modifyFood/{itemId}/edit")
    public String updateItemFood(@PathVariable("itemId") Long itemId, Model model) {
        Food oneFood = itemService.findOneFood(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        FoodAndFileDto foodAndFileDto = new FoodAndFileDto(
                oneFood.getId(),
                oneFood.getName(),
                oneFood.getPrice(),
                oneFood.getQuantity(),
                oneFood.getBrand(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );

        model.addAttribute("foodAndFileDto", foodAndFileDto);

        return "admin/updateItemFood";

    }


    @PostMapping("/modifyFood/{itemId}/edit")
    public String updateFood(@PathVariable Long itemId, @ModelAttribute("form") FoodForm foodForm){
        try {
        itemService.updateFood(itemId,foodForm);
        return "redirect:/modifyFood";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }
    @PostMapping("/deleteBook/{itemId}")
    public String deleteBook(@PathVariable Long itemId){
        log.info("id=================={}", itemId);
        itemService.deleteFileByItemId(itemId);
        itemService.deleteItemByItemId(itemId);
        return "redirect:/modifyBook"; // 적절한 리다이렉션 경로로 수정해야 합니다.
    }




//필요없을듯
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // 파일 경로를 이용하여 실제 파일의 위치를 확인
        String fullPath = fileService.getFullPath(filename);
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

    @GetMapping("uploadFileError")
    public String uploadFileError() {
        return "error/uploadFileError";
    }

    /**
     * 아이템을 저장 할 떄 이미지 파일이 없다면 nullpointexception이 발생하므로 여기서 잡아준다.
     */
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointException(NullPointerException ex) {
        log.error("Exception ={}", ex.getMessage());
        return "redirect:/uploadFileError";
    }

    /**
     *ioException 발생시 이동
     */
    @GetMapping("ioError")
    public String ioError() {
        return "error/ioError";
    }
}
