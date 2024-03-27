package shoppingmall.project.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.QMarket;
import shoppingmall.project.domain.QUser;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.item.QItem;
import shoppingmall.project.repository.custom.MarketRepositoryCustom;

import java.util.List;

@Repository
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MarketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findItemsByUserId(Long userId) {
        QUser user = QUser.user;
        QMarket market = QMarket.market;
        QItem item = QItem.item;

        return queryFactory
                .select(item)
                .from(item)
                .innerJoin(item.markets, market)
                .innerJoin(market.user, user)
                .where(user.id.eq(userId))
                .fetch();
    }

}
