package shoppingmall.project.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.QUploadFile;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.repository.custom.FileRepositoryCustom;

import static shoppingmall.project.domain.QUploadFile.*;

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
}
