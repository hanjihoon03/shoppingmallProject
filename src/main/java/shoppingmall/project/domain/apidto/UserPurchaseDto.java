package shoppingmall.project.domain.apidto;

import lombok.Data;
import shoppingmall.project.domain.subdomain.Address;

import java.time.LocalDateTime;

@Data
public class UserPurchaseDto {
    private Long userId;
    private LocalDateTime purchaseTime;
    private Address address;
    private String itemName;
    private int orderPrice;
    private int orderQuantity;

    public UserPurchaseDto(Long userId, LocalDateTime purchaseTime, Address address, String itemName, int orderPrice, int orderQuantity) {
        this.userId = userId;
        this.purchaseTime = purchaseTime;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }

    public UserPurchaseDto(Long userId, LocalDateTime purchaseTime, Address address) {
        this.userId = userId;
        this.purchaseTime = purchaseTime;
        this.address = address;
    }

    public UserPurchaseDto(String itemName, int orderPrice, int orderQuantity) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }
}
