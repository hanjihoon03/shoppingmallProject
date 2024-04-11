package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Getter
public class Purchase {

    @Id
    @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;
    private String itemName;
    private int orderPrice;
    private int orderQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public Purchase() {
    }

    public Purchase(String itemName, int orderPrice, int orderQuantity) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }

    public Purchase(String itemName, int orderPrice, int orderQuantity, Delivery delivery) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.delivery = delivery;
    }
}
