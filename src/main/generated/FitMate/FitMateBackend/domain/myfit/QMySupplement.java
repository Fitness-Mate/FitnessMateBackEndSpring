package FitMate.FitMateBackend.domain.myfit;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMySupplement is a Querydsl query type for MySupplement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMySupplement extends EntityPathBase<MySupplement> {

    private static final long serialVersionUID = 1174508288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMySupplement mySupplement = new QMySupplement("mySupplement");

    public final QMyFit _super;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Integer> myFitIndex;

    // inherited
    public final FitMate.FitMateBackend.domain.routine.QRoutine routine;

    public final FitMate.FitMateBackend.domain.supplement.QSupplement supplement;

    public QMySupplement(String variable) {
        this(MySupplement.class, forVariable(variable), INITS);
    }

    public QMySupplement(Path<? extends MySupplement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMySupplement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMySupplement(PathMetadata metadata, PathInits inits) {
        this(MySupplement.class, metadata, inits);
    }

    public QMySupplement(Class<? extends MySupplement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QMyFit(type, metadata, inits);
        this.id = _super.id;
        this.myFitIndex = _super.myFitIndex;
        this.routine = _super.routine;
        this.supplement = inits.isInitialized("supplement") ? new FitMate.FitMateBackend.domain.supplement.QSupplement(forProperty("supplement")) : null;
    }

}

