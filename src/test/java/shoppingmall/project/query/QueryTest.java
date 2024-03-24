package shoppingmall.project.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.custom.ItemRepositoryCustom;
import shoppingmall.project.repository.impl.ItemRepositoryImpl;
import shoppingmall.project.service.ItemService;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class QueryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @PersistenceContext
    private EntityManager em;
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);


    @BeforeEach
    void beforeEach() {
        itemService.clear();
    }
    @Test
    public void test() throws Exception{
        //given
        Book book = new Book("a", 1, 1, "b", "c");
        Book book1 = new Book("a1", 2, 2, "b1", "c1");
        Book book2 = new Book("a2", 3, 3, "b2", "c2");

        //when
        itemRepository.save(book);
        itemRepository.save(book1);
        itemRepository.save(book2);

        List<Book> findBook = itemRepository.findBook();
        log.info("findBook={}", findBook.getClass());
        for (Book book3 : findBook) {
            log.info("book={}", book3.getName());
        }
        //then
    }
}
