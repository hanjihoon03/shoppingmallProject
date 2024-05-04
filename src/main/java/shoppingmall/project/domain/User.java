package shoppingmall.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "users_id")
    private Long id;


    private String loginId;

    private String name;


    private Integer age;


    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Tier tier;
    //누적 구매 금액
    private int accumulatedAmount;

    @OneToMany(mappedBy = "user")
    private List<Market> markets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Delivery> deliveries = new ArrayList<>();



    public User(String loginId, String name, Integer age, String email, String password, Address address, Tier tier) {
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.tier = tier;
    }

    public User(String loginId, String name, Integer age, String email, String password, Address address, Tier tier, int accumulatedAmount) {
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.tier = tier;
        this.accumulatedAmount = accumulatedAmount;
    }

    /**
     * 구매 금액 누적
     */
    public int addAmount(int totalPrice) {
        return this.accumulatedAmount = accumulatedAmount + totalPrice;
    }

    /**
     * 사용자가 구매한 구매 가격 누적에 따라 tier가 변경하는 로직
     * @param accumulatedAmount 누적금액 파라미터
     */
    public void upgradeTier(int accumulatedAmount) {

        if (accumulatedAmount >= 2000000) {
            this.tier = Tier.GOLD;
        } else if (accumulatedAmount >= 1500000) {
            this.tier = Tier.SILVER;
        } else if (accumulatedAmount >= 1000000){
            this.tier = Tier.BRONZE;
        }
    }

    /**
     * 회원 가입시 패스워드를 암호화 하기 위한 메서드
     * 회원 가입 이외에 호출 금지
     * @param password
     */
    public void encodePassword(String password) {
        this.password = password;
    }


    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id 반환(고유 값)
    @Override
    public String getUsername() {
        return loginId;
    }
    //사용자 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인
        return true;//true = 만료되지 않음
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금이 되었는지 확인
        return true; //true = 잠금되지 않음
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료됐는지 확인
        return true; //true = 만료되지 않음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        //계정이 사용 가능한지 확인하는 로직
        return true; //true = 사용 가능
    }
}
