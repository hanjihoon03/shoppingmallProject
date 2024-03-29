package shoppingmall.project.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.QMarket;
import shoppingmall.project.domain.QUploadFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.item.QItem;
import shoppingmall.project.repository.custom.MarketRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

import static shoppingmall.project.domain.QMarket.*;
import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.QUser.*;
import static shoppingmall.project.domain.item.QItem.*;

@Repository
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MarketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Item> findItemsByUserId(Long userId) {

        return queryFactory
                .select(item)
                .from(item)
                .innerJoin(item.markets, market)
                .innerJoin(market.user, user)
                .where(user.id.eq(userId))
                .fetch();
    }


    @Override
    public List<ItemDto> findItemAndFile(List<Long> itemIds) {

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(item.id.in(itemIds))
                .fetch();


    }

    @Override
    public void deleteMarketOfItem(Long itemId) {
        queryFactory.delete(market)
                .where(market.items.id.eq(itemId))
                .execute();
    }

    @Override
    public void deleteMarketOfUser(Long userId) {
        queryFactory.delete(market)
                .where(market.id.eq(userId))
                .execute();
    }


}
