package shoppingmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;

    private String uploadFileName;
    private String storeFileName;

    public ItemDto() {
    }

    public ItemDto(Long id, String name, int price, int quantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
