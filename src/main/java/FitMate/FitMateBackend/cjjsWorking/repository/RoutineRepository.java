package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.Routine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoutineRepository {

    private final EntityManager em;

    public Long save(Routine routine) {
        em.persist(routine);
        return routine.getId();
    }

    public Optional<Routine> findById(Long routineId) {
        return Optional.ofNullable(em.find(Routine.class, routineId));
    }

    public List<Routine> findAllWithRoutineIndex(Long userId) {
        return em.createQuery("select r from Routine r" +
                        " join fetch r.user u" +
                        " where u.id = :userId" +
                        " order by r.routineIndex asc", Routine.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void remove(Routine routine) {
        em.remove(routine);
    }
}
