package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyFitRepository {

    private final EntityManager em;

    public void save(MyFit myFit) {
        em.persist(myFit);
    }

    public Optional<MyWorkout> findByMyWorkoutById(Long myWorkoutId) {
        return Optional.ofNullable(em.find(MyWorkout.class, myWorkoutId));
    }

    public List<MyWorkout> findAllMyWorkoutWithRoutineId(Long routineId) {
        return em.createQuery(
                "select w from MyWorkout w" +
                        " where w.routine.id = :routineId" +
                        " order by w.myFitIndex asc", MyWorkout.class)
                .setParameter("routineId", routineId)
                .getResultList();
    }

    public void remove(MyFit myFit) {
        em.remove(myFit);
    }
}
