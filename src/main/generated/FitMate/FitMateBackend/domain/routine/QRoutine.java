package FitMate.FitMateBackend.domain.routine;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoutine is a Querydsl query type for Routine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoutine extends EntityPathBase<Routine> {

    private static final long serialVersionUID = -2097172536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoutine routine = new QRoutine("routine");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<FitMate.FitMateBackend.domain.myfit.MyFit, FitMate.FitMateBackend.domain.myfit.QMyFit> myFit = this.<FitMate.FitMateBackend.domain.myfit.MyFit, FitMate.FitMateBackend.domain.myfit.QMyFit>createList("myFit", FitMate.FitMateBackend.domain.myfit.MyFit.class, FitMate.FitMateBackend.domain.myfit.QMyFit.class, PathInits.DIRECT2);

    public final NumberPath<Integer> routineIndex = createNumber("routineIndex", Integer.class);

    public final StringPath routineName = createString("routineName");

    public final FitMate.FitMateBackend.domain.QUser user;

    public QRoutine(String variable) {
        this(Routine.class, forVariable(variable), INITS);
    }

    public QRoutine(Path<? extends Routine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoutine(PathMetadata metadata, PathInits inits) {
        this(Routine.class, metadata, inits);
    }

    public QRoutine(Class<? extends Routine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new FitMate.FitMateBackend.domain.QUser(forProperty("user")) : null;
    }

}

