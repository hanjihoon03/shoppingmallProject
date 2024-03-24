package shoppingmall.project.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.item.QBook;
import shoppingmall.project.domain.item.QItem;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.custom.ItemRepositoryCustom;

import java.util.Collections;
import java.util.List;

import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QItem.*;


@Repository

@Transactional
public class ItemRepositoryImpl implements ItemRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Book> findBook() {
            return queryFactory.select(book)
                    .from(book)
                    .where(book.dtype.eq("Book"))
                    .fetch();
    }
}
