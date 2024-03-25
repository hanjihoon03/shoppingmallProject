package shoppingmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;
import shoppingmall.project.domain.item.ClothesType;

@Getter
@Setter
public class ClothesAndFileDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String uploadFileName;
    private String storeFileName;
    private ClothesType clothesType;
    private String brand;
    private int size;

    public ClothesAndFileDto() {
    }

    public ClothesAndFileDto(Long id, String name, int price, int quantity, String uploadFileName, String storeFileName, ClothesType clothesType, String brand, int size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
