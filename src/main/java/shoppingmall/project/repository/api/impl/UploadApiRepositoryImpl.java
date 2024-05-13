package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.repository.api.custom.UploadApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class UploadApiRepositoryImpl implements UploadApiRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    public UploadApiRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteFromItemId(Long itemId) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.id.eq(itemId));
    }
    @Override
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
