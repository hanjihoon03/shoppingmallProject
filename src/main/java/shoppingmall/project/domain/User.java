package shoppingmall.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;

    @NotEmpty
    private String age;


    @Column(nullable = false, unique = true)
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Market> markets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    public User(String loginId, String name, String age, String email, String password, Address address) {
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
