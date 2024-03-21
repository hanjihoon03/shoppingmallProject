package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
@Entity
public class Electronics extends Item {
    private String brand;
    private String type;
}
