package FitMate.FitMateBackend.domain.routine;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupplementRoutine is a Querydsl query type for SupplementRoutine
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplementRoutine extends EntityPathBase<SupplementRoutine> {

    private static final long serialVersionUID = 370862031L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupplementRoutine supplementRoutine = new QSupplementRoutine("supplementRoutine");

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

    public QSupplementRoutine(String variable) {
        this(SupplementRoutine.class, forVariable(variable), INITS);
    }

    public QSupplementRoutine(Path<? extends SupplementRoutine> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupplementRoutine(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupplementRoutine(PathMetadata metadata, PathInits inits) {
        this(SupplementRoutine.class, metadata, inits);
    }

    public QSupplementRoutine(Class<? extends SupplementRoutine> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QRoutine(type, metadata, inits);
        this.id = _super.id;
        this.myFit = _super.myFit;
        this.routineIndex = _super.routineIndex;
        this.routineName = _super.routineName;
        this.user = _super.user;
    }

}

