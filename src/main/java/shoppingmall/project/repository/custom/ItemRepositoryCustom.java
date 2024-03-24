package shoppingmall.project.repository.custom;

import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.item.Book;

import java.util.List;

public interface ItemRepositoryCustom{
    List<Book> findBook();
}
