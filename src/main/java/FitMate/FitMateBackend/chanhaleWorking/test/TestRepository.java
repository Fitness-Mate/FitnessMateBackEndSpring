package FitMate.FitMateBackend.chanhaleWorking.test;

import FitMate.FitMateBackend.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TestRepository {
    @PersistenceContext
    private final EntityManager em;
    public TestWriteDomain getTestWrite() {
        TestWriteDomain twd = em.find(TestWriteDomain.class, 1);
        if (twd == null) {
            twd = new TestWriteDomain(1L, ".");
            em.persist(twd);
        }
        return twd;
    }

    public void save(TestWriteDomain twd){
        if (twd.getId() == null) {
            em.persist(twd);
        }else{
            em.merge(twd);
        }
    }
}
