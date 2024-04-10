package shoppingmall.project.aop;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.exception.MyDbException;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.impl.ItemRepositoryImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class DbExceptionAspectTest {

    @Autowired
    private ItemRepository itemRepository;


    @Test
    public void testFindBookById() {

        Book book = new Book(1L,"Test Book", 1, 1, "1234567890", "1");

        itemRepository.save(book);

        Book foundBook = itemRepository.findBook(1L);
        // Assertions
        Assertions.assertThat(book.getName()).isEqualTo(foundBook.getName());
        Assertions.assertThatThrownBy(() -> itemRepository.findBook(1000L))
                .isInstanceOf(MyDbException.class);

    }
}