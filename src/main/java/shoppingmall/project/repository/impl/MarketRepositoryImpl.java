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
//
//        // BooleanExpression 초기화
//        BooleanExpression predicate = null;
//
//        // 주어진 아이디들에 해당하는 경우를 OR 연산하여 조건 구성
//        for (Long itemId : itemIds) {
//            BooleanExpression itemPredicate = item.id.eq(itemId);
//            if (predicate == null) {
//                predicate = itemPredicate;
//            } else {
//                predicate = predicate.or(itemPredicate);
//            }
//        }
//
//        // QueryDSL을 사용하여 아이템과 업로드 파일 정보를 조인하여 조회
//        List<Tuple> tuples = queryFactory.select(item,uploadFile)
//                .from(item)
//                .join(uploadFile).on(item.id.eq(uploadFile.item.id))
//                .where(predicate)
//                .fetch();

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(item.id.in(itemIds))
                .fetch();

//
//        List<ItemDto> itemToDtos = new ArrayList<>();
//        for (Tuple tuple : tuples) {
//            Item item = tuple.get(QItem.item);  // "item"은 엔티티 클래스의 인스턴스를 나타내는 QItem.item이어야 합니다.
//            UploadFile uploadFile = tuple.get(QUploadFile.uploadFile);  // "uploadFile"은 엔티티 클래스의 인스턴스를 나타내는 QUploadFile.uploadFile이어야 합니다.
//
//
//
//            ItemDto itemDto = new ItemDto();
//            itemDto.setId(item.getId());
//            itemDto.setName(item.getName());
//            itemDto.setPrice(item.getPrice());
//            itemDto.setQuantity(item.getQuantity());
//            itemDto.setUploadFileName(uploadFile.getUploadFileName());
//            itemDto.setStoreFileName(uploadFile.getStoreFileName());
//
//            itemToDtos.add(itemDto);
//        }
//
//        return itemToDtos;
    }

    @Override
    public void deleteMarketOfItem(Long itemId) {
        queryFactory.delete(market)
                .where(market.items.id.eq(itemId))
                .execute();
    }


}
