package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import shoppingmall.project.domain.subdomain.Address;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "users_id")
    private Long id;

    private String name;
    private int age;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Market> markets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;
}
