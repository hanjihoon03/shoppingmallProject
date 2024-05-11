package shoppingmall.project.repository.api;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.QUploadFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.item.Book;

import java.util.List;

import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class UploadApiRepository {
    private final JPAQueryFactory queryFactory;

    public UploadApiRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void deleteFromItemId(Long itemId) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.id.eq(itemId));
    }

    public List<BookAndFileDto> findAllBook() {
        return queryFactory.select(Projections.bean(BookAndFileDto.class,
                        uploadFile.item.id,
                        uploadFile.item.name,
                        uploadFile.item.price,
                        uploadFile.item.quantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName,
                        Expressions.as(book.isbn, "isbn"),
                        Expressions.as(book.author, "author")))
                .from(uploadFile)
                .join(uploadFile.item,item)
                .where(item.dtype.eq("Book"))
                .fetch();
    }


}
