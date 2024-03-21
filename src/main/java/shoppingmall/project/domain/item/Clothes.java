package shoppingmall.project.domain.item;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
@Entity
public class Clothes extends Item {
    @Embedded
    private ClothesType clothesType;
    private String brand;
    private int size;
}
