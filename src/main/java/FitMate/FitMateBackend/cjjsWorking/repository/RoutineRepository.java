package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.Routine;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoutineRepository {

    private final EntityManager em;

    public void save(Routine routine) {
        em.persist(routine);
    }
}
