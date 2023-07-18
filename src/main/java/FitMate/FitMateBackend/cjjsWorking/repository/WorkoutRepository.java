package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.QWorkout;
import FitMate.FitMateBackend.domain.Workout;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class WorkoutRepository {

    private final EntityManager em;
    private final BodyPartRepository bodyPartRepository;

    public void save(Workout workout) {
        em.persist(workout);
    }

    public Workout findById(Long id) {
        return em.find(Workout.class, id);
    }
    public Optional<Workout> findByKoreanName(String koreanName) {
        return Optional.ofNullable(em.createQuery("select w from Workout w where w.koreanName = :koreanName", Workout.class)
                .setParameter("koreanName", koreanName)
                .getSingleResult());
    }
    public Optional<Workout> findByEnglishName(String englishName) {
        return Optional.ofNullable(em.createQuery("select w from Workout w where w.englishName = :englishName", Workout.class)
                .setParameter("englishName", englishName)
                .getSingleResult());
    }


    //Overloading
    public List<Workout> findAll(int page) {
        int offset = (page-1)*10;
        int limit = 10;

        return em.createQuery("select w from Workout w order by w.id desc", Workout.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Workout> findAll() {
        return em.createQuery("select w from Workout w order by w.id desc", Workout.class)
                .getResultList();
    }
    //Overloading

    public void remove(Workout workout) {
        em.remove(workout);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Workout> searchAll(int page, WorkoutSearch search) {
        int offset = (page-1)*10;
        int limit = 10;

        BooleanBuilder builder = new BooleanBuilder();
        if(search.getSearchKeyword() != null) {
            builder.or(QWorkout.workout.englishName.like("%" + search.getSearchKeyword() + "%"));
            builder.or(QWorkout.workout.koreanName.like("%" + search.getSearchKeyword() + "%"));
        }
        if(search.getBodyPartKoreanName() != null) {
            for (String koreanName : search.getBodyPartKoreanName()) {
                BodyPart bodyPart = bodyPartRepository.findByKoreanName(koreanName);
                builder.and(QWorkout.workout.bodyParts.contains(bodyPart));
            }
        }

        QWorkout workout = QWorkout.workout;
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(workout)
                .from(workout)
                .where(builder)
                .orderBy(workout.id.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}