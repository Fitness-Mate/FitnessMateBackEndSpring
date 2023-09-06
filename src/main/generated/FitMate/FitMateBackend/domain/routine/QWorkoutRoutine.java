package FitMate.FitMateBackend.domain.routine;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkoutRoutine is a Querydsl query type for WorkoutRoutine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkoutRoutine extends EntityPathBase<WorkoutRoutine> {

    private static final long serialVersionUID = -1350074397L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkoutRoutine workoutRoutine = new QWorkoutRoutine("workoutRoutine");

    public final QRoutine _super;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final ListPath<FitMate.FitMateBackend.domain.myfit.MyFit, FitMate.FitMateBackend.domain.myfit.QMyFit> myFit;

    //inherited
    public final NumberPath<Integer> routineIndex;

    //inherited
    public final StringPath routineName;

    // inherited
    public final FitMate.FitMateBackend.domain.QUser user;

    public QWorkoutRoutine(String variable) {
        this(WorkoutRoutine.class, forVariable(variable), INITS);
    }

    public QWorkoutRoutine(Path<? extends WorkoutRoutine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkoutRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkoutRoutine(PathMetadata metadata, PathInits inits) {
        this(WorkoutRoutine.class, metadata, inits);
    }

    public QWorkoutRoutine(Class<? extends WorkoutRoutine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRoutine(type, metadata, inits);
        this.id = _super.id;
        this.myFit = _super.myFit;
        this.routineIndex = _super.routineIndex;
        this.routineName = _super.routineName;
        this.user = _super.user;
    }

}

