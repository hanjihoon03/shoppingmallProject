package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;

import shoppingmall.project.domain.subdomain.Tier;

@Entity
@Getter
public class Membership {

    @Id @GeneratedValue
    @Column(name = "membership_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Tier tier;
}
