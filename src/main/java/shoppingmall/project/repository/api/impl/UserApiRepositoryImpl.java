package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.apidto.UserDto;
import shoppingmall.project.domain.apidto.UserLoginIdPwDto;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.api.custom.UserApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QUser.*;

@Repository
public class UserApiRepositoryImpl implements UserApiRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserApiRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public UserLoginIdPwDto findByNameAndEmail(String name, String email) {
        return queryFactory.select(Projections.bean(UserLoginIdPwDto.class,
                user.loginId,
                user.password
                ))
                .from(user)
                .where(user.name.eq(name).and(user.email.eq(email)))
                .fetchOne();
    }


    private BooleanExpression tierEq(Tier tier) {
        return tier != null ? user.tier.eq(tier) : null;
    }
}
