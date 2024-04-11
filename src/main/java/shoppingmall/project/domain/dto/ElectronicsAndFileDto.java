package shoppingmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectronicsAndFileDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String brand;
    private String uploadFileName;
    private String storeFileName;

    public ElectronicsAndFileDto() {
    }

    public ElectronicsAndFileDto(Long id, String name, int price, int quantity, String brand, String uploadFileName, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}