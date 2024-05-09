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
import shoppingmall.project.domain.dto.MarketPayDto;
import shoppingmall.project.domain.dto.PurchasePayDto;
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
    public List<ItemDto> findItemAndFile(List<Long> itemIds,Long userId) {

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(item.id.in(itemIds).and(market.user.id.eq(userId)))
                .fetch();
        //where에서 item.id.in(itemIds)없어도 됨 한번에 장바구니 안에 userid기준으로 아이템을 찾을 수 있음


    }

    @Override
    public void deleteMarketOfItem(Long itemId, Long userId) {
        //userId가 현재 세션 사용자라는 것을 조건으로 걸어야 함
        queryFactory.delete(market)
                .where(market.user.id.eq(userId).and(market.items.id.eq(itemId)))
                .execute();
    }

    @Override
    public void deleteMarketOfUser(Long userId) {
        queryFactory.delete(market)
                .where(market.user.id.eq(userId))
                .execute();
    }


    @Override
    public List<MarketPayDto> shoppingBasket(Long id) {
        return queryFactory.select(Projections.bean(MarketPayDto.class,
                market.user.id,
                market.items.name,
                item.price,
                market.orderQuantity
        ))
                .from(market)
                .leftJoin(market.items,item)
                .where(market.user.id.eq(id))
                .fetch();
    }


}
