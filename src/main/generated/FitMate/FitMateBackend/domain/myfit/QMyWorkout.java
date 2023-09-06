package FitMate.FitMateBackend.domain.myfit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyWorkout is a Querydsl query type for MyWorkout
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyWorkout extends EntityPathBase<MyWorkout> {

    private static final long serialVersionUID = 711394742L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyWorkout myWorkout = new QMyWorkout("myWorkout");

    public final QMyFit _super;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> myFitIndex;

    public final StringPath rep = createString("rep");

    // inherited
    public final FitMate.FitMateBackend.domain.routine.QRoutine routine;

    public final StringPath setCount = createString("setCount");

    public final StringPath weight = createString("weight");

    public final FitMate.FitMateBackend.domain.QWorkout workout;

    public QMyWorkout(String variable) {
        this(MyWorkout.class, forVariable(variable), INITS);
    }

    public QMyWorkout(Path<? extends MyWorkout> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyWorkout(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyWorkout(PathMetadata metadata, PathInits inits) {
        this(MyWorkout.class, metadata, inits);
    }

    public QMyWorkout(Class<? extends MyWorkout> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QMyFit(type, metadata, inits);
        this.id = _super.id;
        this.myFitIndex = _super.myFitIndex;
        this.routine = _super.routine;
        this.workout = inits.isInitialized("workout") ? new FitMate.FitMateBackend.domain.QWorkout(forProperty("workout")) : null;
    }

}

