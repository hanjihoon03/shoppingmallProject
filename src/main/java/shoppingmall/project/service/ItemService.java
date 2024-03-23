package shoppingmall.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.repository.ItemRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public void saveBook(BookForm bookForm) {
        Item book = new Book(bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getQuantity(),
                bookForm.getIsbn(),
                bookForm.getAuthor());

        itemRepository.save(book);
    }

    public void saveClothes(ClothesForm clothesForm) {
        Item clothes = new Clothes(
                clothesForm.getName(),
                clothesForm.getPrice(),
                clothesForm.getQuantity(),
                clothesForm.getClothesType(),
                clothesForm.getBrand(),
                clothesForm.getSize());

        itemRepository.save(clothes);
    }

    public void saveFood(FoodForm foodForm) {
        Item food = new Food(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand()
        );
        itemRepository.save(food);
    }

    public void saveElectronics(ElectronicsForm electronicsForm) {
        Item electronics = new Electronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand()
        );

        itemRepository.save(electronics);
    }

}
