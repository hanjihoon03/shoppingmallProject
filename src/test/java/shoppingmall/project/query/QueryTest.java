//package shoppingmall.project.query;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import shoppingmall.project.domain.UploadFile;
//import shoppingmall.project.domain.item.Book;
//import shoppingmall.project.repository.FileRepository;
//import shoppingmall.project.repository.ItemRepository;
//import shoppingmall.project.service.ItemService;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Slf4j
//public class QueryTest {
//    @Autowired
//    ItemRepository itemRepository;
//    @Autowired
//    FileRepository fileRepository;
//    @Autowired
//    ItemService itemService;
//
//
//    @PersistenceContext
//    private EntityManager em;
//    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//
//
//    @BeforeEach
//    void beforeEach() {
//        itemService.clear();
//    }
//    @Test
//    public void test() throws Exception{
//        //given
//        UploadFile uploadFileEx = new UploadFile("123123", "123213123");
//        Book book1 = new Book("a", 1, 1, "b", "c", uploadFileEx);
//        Book book2 = new Book("a1", 2, 2, "b1", "c1",uploadFileEx);
//        Book book3 = new Book("a2", 3, 3, "b2", "c2",uploadFileEx);
//        UploadFile uploadFile = new UploadFile("12345", "123456",book1);
//
//        //when
//        itemRepository.save(book1);
//        itemRepository.save(book2);
//        itemRepository.save(book3);
//        fileRepository.save(uploadFile);
//        em.clear();
//        log.info("query-----------------------------------");
//        List<Book> findBook = itemRepository.findBookWithUploadFile();
//        log.info("query-----------------------------------");
//        log.info("findBook={}", findBook.getClass());
//        //then
//        for (Book book4 : findBook) {
//            log.info("book={}", book4.getName());
//            log.info("book.uploadfile={}", book4.getImageFiles());
//        }
//        assertThat(findBook).isNotNull();
//        assertThat(findBook).hasSize(3); // 세 개의 책이 검색되었는지 확인
//        assertThat(findBook).extracting(Book::getName)
//                .containsExactly("a", "a1", "a2");
//
//    }
//}
