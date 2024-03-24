package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class Food extends Item {
    private String brand;

    public Food(String name, int price, int quantity, String brand) {
        super(name, price, quantity);
        this.brand = brand;
    }
}
