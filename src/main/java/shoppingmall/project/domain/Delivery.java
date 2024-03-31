package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery")
    private Market market;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item items;

    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "delivery")
    private List<Purchase> purchases = new ArrayList<>();
    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}
