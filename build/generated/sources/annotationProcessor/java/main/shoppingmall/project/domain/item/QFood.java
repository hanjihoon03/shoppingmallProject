package shoppingmall.project.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = -1845839306L;

    public static final QFood food = new QFood("food");

    public final QItem _super = new QItem(this);

    public final StringPath brand = createString("brand");

    //inherited
    public final StringPath dtype = _super.dtype;

    //inherited
    public final NumberPath<Long> id = _super.id;

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

    public QFood(String variable) {
        super(Food.class, forVariable(variable));
    }

    public QFood(Path<? extends Food> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(Food.class, metadata);
    }

}

