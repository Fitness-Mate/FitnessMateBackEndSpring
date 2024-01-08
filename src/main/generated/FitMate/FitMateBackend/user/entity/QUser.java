package FitMate.FitMateBackend.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1079873259L;

    public static final QUser user = new QUser("user");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final ListPath<BodyData, QBodyData> bodyDataHistory = this.<BodyData, QBodyData>createList("bodyDataHistory", BodyData.class, QBodyData.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> joinDate = createDate("joinDate", java.time.LocalDate.class);

    public final StringPath loginEmail = createString("loginEmail");

    public final StringPath password = createString("password");

    public final ListPath<FitMate.FitMateBackend.recommend.entity.Recommendation, FitMate.FitMateBackend.recommend.entity.QRecommendation> recommendationHistory = this.<FitMate.FitMateBackend.recommend.entity.Recommendation, FitMate.FitMateBackend.recommend.entity.QRecommendation>createList("recommendationHistory", FitMate.FitMateBackend.recommend.entity.Recommendation.class, FitMate.FitMateBackend.recommend.entity.QRecommendation.class, PathInits.DIRECT2);

    public final ListPath<FitMate.FitMateBackend.myfit.entity.Routine, FitMate.FitMateBackend.myfit.entity.QRoutine> routines = this.<FitMate.FitMateBackend.myfit.entity.Routine, FitMate.FitMateBackend.myfit.entity.QRoutine>createList("routines", FitMate.FitMateBackend.myfit.entity.Routine.class, FitMate.FitMateBackend.myfit.entity.QRoutine.class, PathInits.DIRECT2);

    public final StringPath sex = createString("sex");

    public final StringPath type = createString("type");

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

