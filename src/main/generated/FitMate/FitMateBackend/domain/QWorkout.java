package FitMate.FitMateBackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkout is a Querydsl query type for Workout
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkout extends EntityPathBase<Workout> {

    private static final long serialVersionUID = 291263179L;

    public static final QWorkout workout = new QWorkout("workout");

    public final ListPath<BodyPart, QBodyPart> bodyParts = this.<BodyPart, QBodyPart>createList("bodyParts", BodyPart.class, QBodyPart.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final StringPath englishName = createString("englishName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgFileName = createString("imgFileName");

    public final StringPath koreanName = createString("koreanName");

    public final ListPath<Machine, QMachine> machines = this.<Machine, QMachine>createList("machines", Machine.class, QMachine.class, PathInits.DIRECT2);

    public final StringPath videoLink = createString("videoLink");

    public QWorkout(String variable) {
        super(Workout.class, forVariable(variable));
    }

    public QWorkout(Path<? extends Workout> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkout(PathMetadata metadata) {
        super(Workout.class, metadata);
    }

}

