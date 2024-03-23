package shoppingmall.project.domain.item;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
@Entity
public class Clothes extends Item {
    @Embedded
    private ClothesType clothesType;
    private String brand;
    private int size;

    public Clothes(String name, int price, int quantity, ClothesType clothesType, String brand, int size) {
        super(name, price, quantity);
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
