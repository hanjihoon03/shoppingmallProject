package shoppingmall.project.repository.api;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.dto.ItemDto;

import java.util.List;

import static shoppingmall.project.domain.QMarket.market;
import static shoppingmall.project.domain.QUploadFile.uploadFile;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class MarketApiRepository {
    private final JPAQueryFactory queryFactory;

    public MarketApiRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<ItemDto> findItemAndFile(List<Long> itemIds, Long userId) {

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

    public List<ItemDto> findItemAndFileRefactor(Long userId) {

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(market.user.id.eq(userId))
                .fetch();

    }



}
