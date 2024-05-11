package shoppingmall.project.repository.api;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.apidto.BookApiDto;
import shoppingmall.project.domain.apidto.FoodApiDto;
import shoppingmall.project.domain.apidto.ItemCond;
import shoppingmall.project.domain.item.*;

import java.util.List;

import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QClothes.clothes;
import static shoppingmall.project.domain.item.QElectronics.electronics;
import static shoppingmall.project.domain.item.QFood.food;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class ItemApiRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public ItemApiRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    //api
    public List<Item> findPriceRange(ItemCond itemCond) {
        return queryFactory.selectFrom(item)
                .where(item.price.between(itemCond.getMin(), itemCond.getMax()))
                .fetch();
    }


    public List<Item> findDtypePriceRange(ItemCond itemCond) {
        return queryFactory.selectFrom(item)
                .where(item.dtype.eq(itemCond.getDtype()).and(item.price.between(itemCond.getMin(), itemCond.getMax())))
                .fetch();
    }


    public Book updateBook(Long id) {
        return queryFactory.select(book)
                .from(book)
                .where((book.id.eq(id)))
                .fetchOne();
    }

    public Clothes updateClothes(Long id) {
        return queryFactory.select(clothes)
                .from(clothes)
                .where((clothes.id.eq(id)))
                .fetchOne();
    }

    public Electronics updateElectronics(Long id) {
        return queryFactory.select(electronics)
                .from(electronics)
                .where((electronics.id.eq(id)))
                .fetchOne();
    }

    public Food updateFood(Long id) {
        return queryFactory.select(food)
                .from(food)
                .where((food.id.eq(id)))
                .fetchOne();
    }

    public List<BookApiDto> findAllBook() {
       return queryFactory.select(Projections.constructor(BookApiDto.class,
                item.id,
                item.name,
                item.price,
                item.quantity,
                book.isbn,
                book.author
                ))
                .from(item)
                .leftJoin(book)
               .on(item.id.eq(book.id))
                .fetch();
    }

    public Page<BookApiDto> findAllBookPaging(Pageable pageable) {
        List<BookApiDto> content = queryFactory.select(Projections.constructor(BookApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        book.isbn,
                        book.author
                ))
                .from(item)
                .leftJoin(book)
                .on(item.id.eq(book.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(item.count())
                .from(item)
                .leftJoin(book)
                .on(item.id.eq(book.id))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }


    public List<BookApiDto> jpqlPaging(int offset, int limit) {
        return entityManager.createQuery("SELECT NEW shoppingmall.project.domain.apidto.BookApiDto("
                + "item.id, "
                + "item.name, "
                + "item.price, "
                + "item.quantity, "
                + "book.isbn, "
                + "book.author) "
                + "FROM Item item "
                + "JOIN fetch Book book "
                + "ON item.id = book.id "
                , BookApiDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


}
