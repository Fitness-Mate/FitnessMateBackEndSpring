package FitMate.FitMateBackend.domain.supplement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import FitMate.FitMateBackend.supplement.entity.Supplement;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSupplement is a Querydsl query type for Supplement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplement extends EntityPathBase<Supplement> {

    private static final long serialVersionUID = -1239984900L;

    public static final QSupplement supplement = new QSupplement("supplement");

    public final StringPath description = createString("description");

    public final StringPath englishName = createString("englishName");

    public final StringPath flavor = createString("flavor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageName = createString("imageName");

    public final BooleanPath isCaptain = createBoolean("isCaptain");

    public final StringPath koreanName = createString("koreanName");

    public final StringPath marketURL = createString("marketURL");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Float> servings = createNumber("servings", Float.class);

    public final EnumPath<SupplementType> type = createEnum("type", SupplementType.class);

    public QSupplement(String variable) {
        super(Supplement.class, forVariable(variable));
    }

    public QSupplement(Path<? extends Supplement> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSupplement(PathMetadata metadata) {
        super(Supplement.class, metadata);
    }

}

