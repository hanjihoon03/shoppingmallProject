package shoppingmall.project.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.domain.apidto.ItemApiDto;
import shoppingmall.project.domain.apidto.UpdateItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.api.ItemApiService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemApiService itemApiService;

    //모든 item data 응답
    @GetMapping("/api/item")
    public List<ItemApiDto> allItemSearch() {
        return itemApiService.findAllItem();
    }

    //원하는 타입의 아이템 데이터 응답
    @GetMapping("/api/item/{dtype}")
    public ResponseEntity<List<ItemApiDto>> allDtypeSearch(@PathVariable String dtype) {
        List<ItemApiDto> allDtype = itemApiService.findAllDtype(dtype);

        return ResponseEntity.ok()
                .body(allDtype);
    }
    ///api/item/condition?min=10&max=20 요청으로 해당하는 숫자 사이의 가격을 가진 아이템을 보여줌
    @GetMapping("/api/item/condition")
    public ResponseEntity<List<ItemApiDto>> getItemByRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        List<ItemApiDto> rangePriceItem = itemApiService.findPriceRange(min, max);

        return ResponseEntity.ok()
                .body(rangePriceItem);
    }

    @GetMapping("/api/item/condition/{dtype}")
    public ResponseEntity<List<ItemApiDto>> getItemDtypeByRange(@PathVariable String dtype, @RequestParam("min") int min, @RequestParam("max") int max) {
        List<ItemApiDto> rangePriceItem = itemApiService.findDtypePriceRange(dtype, min, max);

        return ResponseEntity.ok()
                .body(rangePriceItem);
    }

    @PostMapping("/api/item/{id}")
    public ResponseEntity<ItemApiDto> updateItem(@PathVariable Long id,
                                                 @RequestBody UpdateItemDto request) {
        ItemApiDto itemApiDto = itemApiService.updateItem(id, request);
        return ResponseEntity.ok()
                .body(itemApiDto);
    }
    @DeleteMapping("/api/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemApiService.deleteByItemId(id);

        return ResponseEntity.ok()
                .build();
    }



}
