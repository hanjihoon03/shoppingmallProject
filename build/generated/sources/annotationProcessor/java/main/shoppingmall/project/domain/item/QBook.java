package shoppingmall.project.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1845958463L;

    public static final QBook book = new QBook("book");

    public final QItem _super = new QItem(this);

    public final StringPath author = createString("author");

    //inherited
    public final StringPath dtype = _super.dtype;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath isbn = createString("isbn");

    //inherited
    public final ListPath<shoppingmall.project.domain.Market, shoppingmall.project.domain.QMarket> markets = _super.markets;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> quantity = _super.quantity;

    //inherited
    public final ListPath<shoppingmall.project.domain.UploadFile, shoppingmall.project.domain.QUploadFile> uploadFiles = _super.uploadFiles;

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

