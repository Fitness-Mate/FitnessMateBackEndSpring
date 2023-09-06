package FitMate.FitMateBackend.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendation is a Querydsl query type for Recommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendation extends EntityPathBase<Recommendation> {

    private static final long serialVersionUID = 1537574012L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendation recommendation = new QRecommendation("recommendation");

    public final FitMate.FitMateBackend.domain.QBodyData bodyData;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath queryText = createString("queryText");

    public final StringPath recommendationType = createString("recommendationType");

    public final FitMate.FitMateBackend.domain.QUser user;

    public QRecommendation(String variable) {
        this(Recommendation.class, forVariable(variable), INITS);
    }

    public QRecommendation(Path<? extends Recommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendation(PathMetadata metadata, PathInits inits) {
        this(Recommendation.class, metadata, inits);
    }

    public QRecommendation(Class<? extends Recommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bodyData = inits.isInitialized("bodyData") ? new FitMate.FitMateBackend.domain.QBodyData(forProperty("bodyData"), inits.get("bodyData")) : null;
        this.user = inits.isInitialized("user") ? new FitMate.FitMateBackend.domain.QUser(forProperty("user")) : null;
    }

}

