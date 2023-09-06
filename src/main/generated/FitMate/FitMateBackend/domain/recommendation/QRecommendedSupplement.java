package FitMate.FitMateBackend.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendedSupplement is a Querydsl query type for RecommendedSupplement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendedSupplement extends EntityPathBase<RecommendedSupplement> {

    private static final long serialVersionUID = -1690302639L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendedSupplement recommendedSupplement = new QRecommendedSupplement("recommendedSupplement");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final StringPath koreanRecommendationString = createString("koreanRecommendationString");

    public final FitMate.FitMateBackend.domain.supplement.QSupplement supplement;

    public final QSupplementRecommendation supplementRecommendation;

    public QRecommendedSupplement(String variable) {
        this(RecommendedSupplement.class, forVariable(variable), INITS);
    }

    public QRecommendedSupplement(Path<? extends RecommendedSupplement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendedSupplement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendedSupplement(PathMetadata metadata, PathInits inits) {
        this(RecommendedSupplement.class, metadata, inits);
    }

    public QRecommendedSupplement(Class<? extends RecommendedSupplement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.supplement = inits.isInitialized("supplement") ? new FitMate.FitMateBackend.domain.supplement.QSupplement(forProperty("supplement")) : null;
        this.supplementRecommendation = inits.isInitialized("supplementRecommendation") ? new QSupplementRecommendation(forProperty("supplementRecommendation"), inits.get("supplementRecommendation")) : null;
    }

}

