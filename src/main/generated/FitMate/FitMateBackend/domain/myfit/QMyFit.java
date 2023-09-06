package FitMate.FitMateBackend.domain.myfit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyFit is a Querydsl query type for MyFit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyFit extends EntityPathBase<MyFit> {

    private static final long serialVersionUID = -1291336790L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyFit myFit = new QMyFit("myFit");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> myFitIndex = createNumber("myFitIndex", Integer.class);

    public final FitMate.FitMateBackend.domain.routine.QRoutine routine;

    public QMyFit(String variable) {
        this(MyFit.class, forVariable(variable), INITS);
    }

    public QMyFit(Path<? extends MyFit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyFit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyFit(PathMetadata metadata, PathInits inits) {
        this(MyFit.class, metadata, inits);
    }

    public QMyFit(Class<? extends MyFit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.routine = inits.isInitialized("routine") ? new FitMate.FitMateBackend.domain.routine.QRoutine(forProperty("routine"), inits.get("routine")) : null;
    }

}

