package FitMate.FitMateBackend.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupplementRecommendation is a Querydsl query type for SupplementRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplementRecommendation extends EntityPathBase<SupplementRecommendation> {

    private static final long serialVersionUID = -606004779L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupplementRecommendation supplementRecommendation = new QSupplementRecommendation("supplementRecommendation");

    public final QRecommendation _super;

    // inherited
    public final FitMate.FitMateBackend.domain.QBodyData bodyData;

    //inherited
    public final DatePath<java.time.LocalDate> date;

    //inherited
    public final NumberPath<Long> id;

    public final NumberPath<Long> monthlyBudget = createNumber("monthlyBudget", Long.class);

    public final StringPath purposes = createString("purposes");

    //inherited
    public final StringPath queryText;

    //inherited
    public final StringPath recommendationType;

    public final ListPath<RecommendedSupplement, QRecommendedSupplement> recommendedSupplements = this.<RecommendedSupplement, QRecommendedSupplement>createList("recommendedSupplements", RecommendedSupplement.class, QRecommendedSupplement.class, PathInits.DIRECT2);

    // inherited
    public final FitMate.FitMateBackend.domain.QUser user;

    public QSupplementRecommendation(String variable) {
        this(SupplementRecommendation.class, forVariable(variable), INITS);
    }

    public QSupplementRecommendation(Path<? extends SupplementRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupplementRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupplementRecommendation(PathMetadata metadata, PathInits inits) {
        this(SupplementRecommendation.class, metadata, inits);
    }

    public QSupplementRecommendation(Class<? extends SupplementRecommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRecommendation(type, metadata, inits);
        this.bodyData = _super.bodyData;
        this.date = _super.date;
        this.id = _super.id;
        this.queryText = _super.queryText;
        this.recommendationType = _super.recommendationType;
        this.user = _super.user;
    }

}

