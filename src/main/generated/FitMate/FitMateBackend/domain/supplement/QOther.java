package FitMate.FitMateBackend.domain.supplement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOther is a Querydsl query type for Other
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOther extends EntityPathBase<Other> {

    private static final long serialVersionUID = -129424147L;

    public static final QOther other = new QOther("other");

    public final QSupplement _super = new QSupplement(this);

    public final StringPath contains = createString("contains");

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final StringPath englishName = _super.englishName;

    //inherited
    public final StringPath flavor = _super.flavor;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath imageName = _super.imageName;

    //inherited
    public final BooleanPath isCaptain = _super.isCaptain;

    //inherited
    public final StringPath koreanName = _super.koreanName;

    //inherited
    public final StringPath marketURL = _super.marketURL;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Float> servings = _super.servings;

    //inherited
    public final EnumPath<SupplementType> type = _super.type;

    public QOther(String variable) {
        super(Other.class, forVariable(variable));
    }

    public QOther(Path<? extends Other> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOther(PathMetadata metadata) {
        super(Other.class, metadata);
    }

}

