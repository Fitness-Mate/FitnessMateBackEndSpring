package FitMate.FitMateBackend.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkoutRecommendation is a Querydsl query type for WorkoutRecommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkoutRecommendation extends EntityPathBase<WorkoutRecommendation> {

    private static final long serialVersionUID = -1565564525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkoutRecommendation workoutRecommendation = new QWorkoutRecommendation("workoutRecommendation");

    public final QRecommendation _super;

    // inherited
    public final FitMate.FitMateBackend.domain.QBodyData bodyData;

    //inherited
    public final DatePath<java.time.LocalDate> date;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath queryText;

    //inherited
    public final StringPath recommendationType;

    public final StringPath requestedBodyParts = createString("requestedBodyParts");

    public final StringPath requestedMachines = createString("requestedMachines");

    public final ListPath<RecommendedWorkout, QRecommendedWorkout> rws = this.<RecommendedWorkout, QRecommendedWorkout>createList("rws", RecommendedWorkout.class, QRecommendedWorkout.class, PathInits.DIRECT2);

    // inherited
    public final FitMate.FitMateBackend.domain.QUser user;

    public QWorkoutRecommendation(String variable) {
        this(WorkoutRecommendation.class, forVariable(variable), INITS);
    }

    public QWorkoutRecommendation(Path<? extends WorkoutRecommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkoutRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkoutRecommendation(PathMetadata metadata, PathInits inits) {
        this(WorkoutRecommendation.class, metadata, inits);
    }

    public QWorkoutRecommendation(Class<? extends WorkoutRecommendation> type, PathMetadata metadata, PathInits inits) {
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

