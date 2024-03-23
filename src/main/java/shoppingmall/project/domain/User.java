package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.subdomain.Address;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
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

    public User(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
