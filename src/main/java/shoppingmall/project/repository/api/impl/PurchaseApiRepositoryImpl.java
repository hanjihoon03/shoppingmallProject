package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.*;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.repository.api.custom.PurchaseApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QDelivery.*;
import static shoppingmall.project.domain.QPurchase.*;
import static shoppingmall.project.domain.QUser.*;

@Repository
public class PurchaseApiRepositoryImpl implements PurchaseApiRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public PurchaseApiRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserPurchaseDto> purchaseList(Long id) {
        return queryFactory.select(Projections.constructor(UserPurchaseDto.class,
                purchase.user.id,
                delivery.orderTime,
                delivery.address,
                purchase.itemName,
                purchase.orderPrice,
                purchase.orderQuantity
                ))
                .from(purchase)
                .innerJoin(purchase.delivery,delivery)
                .innerJoin(purchase.user,user)
                .where(purchase.user.id.eq(id))
                .fetch();
    }
}
