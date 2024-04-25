package shoppingmall.project.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.QUploadFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.item.QBook;
import shoppingmall.project.domain.item.QItem;
import shoppingmall.project.repository.custom.FileRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.item.QBook.*;
import static shoppingmall.project.domain.item.QItem.*;

@Repository
public class FileRepositoryImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public FileRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public UploadFile findByItemId(Long id) {
        return queryFactory.select(uploadFile)
                .from(uploadFile)
                .where(uploadFile.item.id.eq(id)).fetchOne();
    }
    @Override
    public void deleteFromItemId(Long id) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.id.eq(id))
                .execute();
    }
}
