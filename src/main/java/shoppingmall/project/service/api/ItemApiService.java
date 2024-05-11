package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.apidto.*;
import shoppingmall.project.domain.apidto.save.BookSaveApiDto;
import shoppingmall.project.domain.apidto.save.ClothesSaveApiDto;
import shoppingmall.project.domain.apidto.save.ElectronicsSaveApiDto;
import shoppingmall.project.domain.apidto.save.FoodSaveApiDto;
import shoppingmall.project.domain.apidto.update.*;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.api.ItemApiRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemApiService {

    private final ItemApiRepository ItemApiRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<ItemApiDto> findAllItem() {
        List<Item> allItem = itemRepository.findAll();

        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                        }).toList();
    }
    @Transactional(readOnly = true)
    public List<ItemApiDto> findAllDtype(String dtype) {
        List<Item> allItem = itemRepository.findByDtype(dtype);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    @Transactional(readOnly = true)
    public List<ItemApiDto> findPriceRange(int min, int max) {
        ItemCond itemCond = new ItemCond(min, max);
        List<Item> allItem = ItemApiRepository.findPriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }
    @Transactional(readOnly = true)
    public List<ItemApiDto> findDtypePriceRange(String dtype, int min, int max) {
        ItemCond itemCond = new ItemCond(min, max, dtype);
        List<Item> allItem = ItemApiRepository.findDtypePriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    public ItemApiDto updateItem(Long id, UpdateItemDto request) {
        Optional<Item> byId = itemRepository.findById(id);


        Item item = byId.orElseThrow(() -> new IllegalArgumentException("not found:" + id));
        item.updateItem(request.getName(), request.getPrice(), request.getQuantity());
        ItemApiDto itemApiDto = new ItemApiDto();
        itemApiDto.setId(item.getId());
        itemApiDto.setName(item.getName());
        itemApiDto.setPrice(item.getPrice());
        itemApiDto.setQuantity(item.getQuantity());
        itemApiDto.setDtype(item.getDtype());

        return itemApiDto;

    }

    public void deleteByItemId(Long id) {
        itemRepository.deleteById(id);
    }

    public void updateApiBook(Long id, UpdateBookDto request) {
        Book book = ItemApiRepository.updateBook(id);
        book.updateBook(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getIsbn(),
                request.getAuthor()
                );
    }
    @Transactional(readOnly = true)
    public BookApiDto findBook(Long id) {
        Book book = itemRepository.findBook(id);
        return new BookApiDto(
                book.getId(),
                book.getName(),
                book.getPrice(),
                book.getQuantity(),
                book.getIsbn(),
                book.getAuthor()
        );
    }

    public void updateApiClothes(Long id, UpdateClothesDto request) {
        Clothes clothes = ItemApiRepository.updateClothes(id);
        clothes.updateClothes(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getClothesType(),
                request.getBrand(),
                request.getSize()
        );
    }
    @Transactional(readOnly = true)
    public ClothesApiDto findClothes(Long id) {
        Clothes clothes = itemRepository.findClothes(id);
        return new ClothesApiDto(
                clothes.getId(),
                clothes.getName(),
                clothes.getPrice(),
                clothes.getQuantity(),
                clothes.getClothesType(),
                clothes.getBrand(),
                clothes.getSize()
        );
    }

    public void updateApiElectronics(Long id, UpdateElectronicsDto request) {
        Electronics electronics = ItemApiRepository.updateElectronics(id);
        electronics.updateElectronics(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
    }
    @Transactional(readOnly = true)
    public ElectronicsApiDto findElectronics(Long id) {
        Electronics electronics = itemRepository.findElectronics(id);
        return new ElectronicsApiDto(
                electronics.getId(),
                electronics.getName(),
                electronics.getPrice(),
                electronics.getQuantity(),
                electronics.getBrand()
        );
    }

    public void updateApiFood(Long id, UpdateFoodDto request) {
        Food food = ItemApiRepository.updateFood(id);
        food.updateFood(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
    }
    @Transactional(readOnly = true)
    public FoodApiDto findFood(Long id) {
        Food food = itemRepository.findFood(id);
        return new FoodApiDto(
                food.getId(),
                food.getName(),
                food.getPrice(),
                food.getQuantity(),
                food.getBrand()
        );
    }

    public BookApiDto saveBook(BookSaveApiDto request) {
        Book book = new Book(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getIsbn(),
                request.getAuthor()
        );
        Book saveBook = itemRepository.save(book);
        return new BookApiDto(
                saveBook.getId(),
                saveBook.getName(),
                saveBook.getPrice(),
                saveBook.getQuantity(),
                saveBook.getIsbn(),
                saveBook.getAuthor()
        );
    }

    public ClothesApiDto saveClothes(ClothesSaveApiDto request) {
        Clothes clothes = new Clothes(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getClothesType(),
                request.getBrand(),
                request.getSize()
        );
        Clothes saveClothes = itemRepository.save(clothes);
        return new ClothesApiDto(
                saveClothes.getId(),
                saveClothes.getName(),
                saveClothes.getPrice(),
                saveClothes.getQuantity(),
                saveClothes.getClothesType(),
                saveClothes.getBrand(),
                saveClothes.getSize()
        );
    }

    public ElectronicsApiDto saveElectronics(ElectronicsSaveApiDto request) {
        Electronics electronics = new Electronics(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
        Electronics saveElectronics = itemRepository.save(electronics);
        return new ElectronicsApiDto(
                saveElectronics.getId(),
                saveElectronics.getName(),
                saveElectronics.getPrice(),
                saveElectronics.getQuantity(),
                saveElectronics.getBrand()
        );
    }

    public FoodApiDto saveFood(FoodSaveApiDto request) {
        Food food = new Food(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
        Food saveFood = itemRepository.save(food);
        return new FoodApiDto(
                saveFood.getId(),
                saveFood.getName(),
                saveFood.getPrice(),
                saveFood.getQuantity(),
                saveFood.getBrand()
        );
    }

    @Transactional(readOnly = true)
    public Page<BookApiDto> findAllBookPaging(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return ItemApiRepository.findAllBookPaging(pageable);
    }
    @Transactional(readOnly = true)
    public List<BookApiDto> findAllBookRe() {
        return ItemApiRepository.findAllBook();
    }
    @Transactional(readOnly = true)
    public List<BookApiDto> jpqlPaging(int offset,int limit) {
        return ItemApiRepository.jpqlPaging(offset,limit);
    }


}
