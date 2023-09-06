package FitMate.FitMateBackend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMachine is a Querydsl query type for Machine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMachine extends EntityPathBase<Machine> {

    private static final long serialVersionUID = -408595339L;

    public static final QMachine machine = new QMachine("machine");

    public final ListPath<BodyPart, QBodyPart> bodyParts = this.<BodyPart, QBodyPart>createList("bodyParts", BodyPart.class, QBodyPart.class, PathInits.DIRECT2);

    public final StringPath englishName = createString("englishName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath koreanName = createString("koreanName");

    public final ListPath<Workout, QWorkout> workouts = this.<Workout, QWorkout>createList("workouts", Workout.class, QWorkout.class, PathInits.DIRECT2);

    public QMachine(String variable) {
        super(Machine.class, forVariable(variable));
    }

    public QMachine(Path<? extends Machine> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMachine(PathMetadata metadata) {
        super(Machine.class, metadata);
    }

}

