package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementSearchForm;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import FitMate.FitMateBackend.domain.supplement.SupplementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SupplementRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Supplement supplement){
        if (supplement.getId() == null) {
            em.persist(supplement);
        }else{
            em.merge(supplement);
        }
    }

    public Supplement findById(Long id) {
        return em.find(Supplement.class, id);
    }

    public void deleteSupplement(Long id) {
        Supplement supplement = em.find(Supplement.class, id);
        if (!(supplement == null)) {
            em.remove(supplement);
        }
    }
    public List<Supplement> findAll(){
        return em.createQuery("select s from Supplement s", Supplement.class).getResultList();}

    public List<Supplement> getSupplementBatch(Long page) {
        return em.createQuery("select s from Supplement s order by s.id", Supplement.class)
                .setFirstResult((int) (ServiceConst.PAGE_BATCH_SIZE * (page - 1))).setMaxResults(ServiceConst.PAGE_BATCH_SIZE)
                .getResultList();
    }

    public List<Supplement> searchSupplement(Long page, List<SupplementType> typeList, String matchString) {
        if (typeList == null || typeList.size() == 0) {
            typeList = List.of(SupplementType.values());
        }
        List<Supplement> supplementList = em.createQuery("select s from Supplement s where s.type in :typ and (s.koreanName like :str or s.flavor like :str or s.englishName like :str) order by s.id", Supplement.class)
                .setParameter("str", "%"+matchString.trim()+"%")
                .setParameter("typ", typeList)
                .setFirstResult((int) (ServiceConst.PAGE_BATCH_SIZE * (page - 1))).setMaxResults(ServiceConst.PAGE_BATCH_SIZE)
                .getResultList();
        log.info("{}",supplementList.size());
        return supplementList;
    }
}
