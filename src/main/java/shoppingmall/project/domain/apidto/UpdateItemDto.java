package shoppingmall.project.domain.apidto;

import lombok.Data;

@Data
public class UpdateItemDto {

    private String name;

    private int price;
    private int quantity;
    public UpdateItemDto() {
    }

    public UpdateItemDto(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
