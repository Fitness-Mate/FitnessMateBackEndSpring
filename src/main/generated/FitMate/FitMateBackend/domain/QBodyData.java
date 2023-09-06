package FitMate.FitMateBackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBodyData is a Querydsl query type for BodyData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBodyData extends EntityPathBase<BodyData> {

    private static final long serialVersionUID = 2105733630L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBodyData bodyData = new QBodyData("bodyData");

    public final NumberPath<Float> bodyFat = createNumber("bodyFat", Float.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Float> height = createNumber("height", Float.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Float> muscleMass = createNumber("muscleMass", Float.class);

    public final NumberPath<Float> upDownBalance = createNumber("upDownBalance", Float.class);

    public final QUser user;

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QBodyData(String variable) {
        this(BodyData.class, forVariable(variable), INITS);
    }

    public QBodyData(Path<? extends BodyData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBodyData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBodyData(PathMetadata metadata, PathInits inits) {
        this(BodyData.class, metadata, inits);
    }

    public QBodyData(Class<? extends BodyData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

