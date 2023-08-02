package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.consts.ServiceConst;
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
    private final BodyPartService bodyPartService;

    public void save(Workout workout) {
        em.persist(workout);
    }

    public Workout findById(Long id) {
        return em.find(Workout.class, id);
    }
    public Optional<Workout> findByKoreanName(String koreanName) {
        return em.createQuery("select w from Workout w where w.koreanName = :koreanName", Workout.class)
                .setParameter("koreanName", koreanName)
                .getResultList()
                .stream().findAny();
    }
    public Optional<Workout> findByEnglishName(String englishName) {
        return em.createQuery("select w from Workout w where w.englishName = :englishName", Workout.class)
                .setParameter("englishName", englishName)
                .getResultList()
                .stream().findAny();
    }

    //Overloading
    public List<Workout> findAll(int page) {
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

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
        //SearchKeyword에서 , 공백 / 등 특수문자 제거해야함
        //예를 들어, 렛 풀 다운, 팩덱 플라이를 검색했을때.. 렛 풀 다운과 펙덱 플라이를 같이 검색해줘야 하는데
        //workout에 forSearch 변수 만들고 이름 띄어쓰기 제거해서 저장..
        //검색 할때는 replaceAll() 해서 split후에 검색 -> 리스트 저장 후 리턴
        int offset = (page-1)*ServiceConst.PAGE_BATCH_SIZE;
        int limit = ServiceConst.PAGE_BATCH_SIZE;

        BooleanBuilder builder = new BooleanBuilder();
        if(search.getSearchKeyword() != null) {
            builder.or(QWorkout.workout.englishName.like("%" + search.getSearchKeyword() + "%"));
            builder.or(QWorkout.workout.koreanName.like("%" + search.getSearchKeyword() + "%"));
        }
        if(search.getBodyPartKoreanName() != null) {
            for (String koreanName : search.getBodyPartKoreanName()) {
                BodyPart bodyPart = bodyPartService.findByKoreanName(koreanName);
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