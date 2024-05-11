package shoppingmall.project.repository.api;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.apidto.UserDto;
import shoppingmall.project.domain.apidto.UserLoginIdPwDto;
import shoppingmall.project.domain.subdomain.Tier;

import java.util.List;

import static shoppingmall.project.domain.QUser.*;

@Repository
public class UserApiRepository{
    private final JPAQueryFactory queryFactory;

    public UserApiRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * where 다중 파라미터를 사용한 user의 티어에 따라 파라미터로 받은 age보다 같거나 큰 user들을 반환하는 API
     * 서비스와 컨트롤러가 필요함.
     * @param tier
     * @param age
     * @return
     */

    public List<UserDto> findUserTierAndAge(Tier tier, int age) {

       return queryFactory.select(Projections.bean(UserDto.class,
                user.id,
                user.loginId,
                user.name,
                user.age,
                user.email,
                user.address,
                user.tier,
                user.accumulatedAmount
                        ))
                .from(user)
                .where(tierEq(tier).and(user.age.goe(age)))
                .fetch();
    }


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
