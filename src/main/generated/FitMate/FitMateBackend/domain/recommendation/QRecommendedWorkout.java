package FitMate.FitMateBackend.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendedWorkout is a Querydsl query type for RecommendedWorkout
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendedWorkout extends EntityPathBase<RecommendedWorkout> {

    private static final long serialVersionUID = 1733319429L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendedWorkout recommendedWorkout = new QRecommendedWorkout("recommendedWorkout");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath repeats = createString("repeats");

    public final StringPath sets = createString("sets");

    public final StringPath weight = createString("weight");

    public final FitMate.FitMateBackend.domain.QWorkout workout;

    public final QWorkoutRecommendation workoutRecommendation;

    public QRecommendedWorkout(String variable) {
        this(RecommendedWorkout.class, forVariable(variable), INITS);
    }

    public QRecommendedWorkout(Path<? extends RecommendedWorkout> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendedWorkout(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendedWorkout(PathMetadata metadata, PathInits inits) {
        this(RecommendedWorkout.class, metadata, inits);
    }

    public QRecommendedWorkout(Class<? extends RecommendedWorkout> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workout = inits.isInitialized("workout") ? new FitMate.FitMateBackend.domain.QWorkout(forProperty("workout"), inits.get("workout")) : null;
        this.workoutRecommendation = inits.isInitialized("workoutRecommendation") ? new QWorkoutRecommendation(forProperty("workoutRecommendation"), inits.get("workoutRecommendation")) : null;
    }

}

