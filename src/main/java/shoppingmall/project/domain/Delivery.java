package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.DeliveryStatus;

@Getter
@Entity
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
}
