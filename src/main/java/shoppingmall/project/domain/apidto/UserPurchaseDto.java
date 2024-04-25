package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import shoppingmall.project.domain.subdomain.Address;

import java.time.LocalDateTime;

/**
 * user가 구매한 정보를 가져오기 위한 필요 정보를 필드를 담은 dto
 */
@Data
@Schema(description = "find user PurchaseList")
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
