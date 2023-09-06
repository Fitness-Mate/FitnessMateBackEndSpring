package FitMate.FitMateBackend.domain.supplement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGainer is a Querydsl query type for Gainer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGainer extends EntityPathBase<Gainer> {

    private static final long serialVersionUID = 36276783L;

    public static final QGainer gainer = new QGainer("gainer");

    public final QSupplement _super = new QSupplement(this);

    public final NumberPath<Float> carbohydratePerServing = createNumber("carbohydratePerServing", Float.class);

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final StringPath englishName = _super.englishName;

    public final NumberPath<Float> fatPerServing = createNumber("fatPerServing", Float.class);

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

    public final NumberPath<Float> proteinPerServing = createNumber("proteinPerServing", Float.class);

    //inherited
    public final NumberPath<Float> servings = _super.servings;

    public final StringPath source = createString("source");

    //inherited
    public final EnumPath<SupplementType> type = _super.type;

    public QGainer(String variable) {
        super(Gainer.class, forVariable(variable));
    }

    public QGainer(Path<? extends Gainer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGainer(PathMetadata metadata) {
        super(Gainer.class, metadata);
    }

}

