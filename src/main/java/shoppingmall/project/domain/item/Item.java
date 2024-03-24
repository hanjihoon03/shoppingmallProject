package shoppingmall.project.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;

    private int price;
    private int quantity;
    @Column(name = "dtype", insertable = false, updatable = false)
    private String dtype;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "item")
    private List<UploadFile> uploadFiles = new ArrayList<>();

    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
