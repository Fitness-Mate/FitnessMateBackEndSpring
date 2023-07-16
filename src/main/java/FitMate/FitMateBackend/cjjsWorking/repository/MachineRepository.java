package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.QMachine;
import FitMate.FitMateBackend.domain.QWorkout;
import FitMate.FitMateBackend.domain.recommendation.QWorkoutRecommendation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MachineRepository {

    private final EntityManager em;
    private final BodyPartRepository bodyPartRepository;

    public void save(Machine machine) {
        em.persist(machine);
    }

    public Machine findById(Long id) {
        return em.find(Machine.class, id);
    }

    public Machine findByKoreanName(String koreanName) {
        return em.createQuery("select m from Machine m where m.koreanName = :koreanName", Machine.class)
                .setParameter("koreanName", koreanName)
                .getResultList().get(0);
    }

    // Overloading
    public List<Machine> findAll() {
        return em.createQuery("select m from Machine m order by m.id desc", Machine.class)
                .getResultList();
    }
    public List<Machine> findAll(int page) {
        int offset = (page-1)*10;
        int limit = 10;

        return em.createQuery("select m from Machine m order by m.id desc", Machine.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    // Overloading

    public void remove(Machine machine) {
        em.remove(machine);
    }

    public List<Machine> findByMachineKoreanName(List<String> machineKoreanName) {
        if (machineKoreanName == null) {
            return null;
        }
        List<Machine> machines = new ArrayList<>();
        for (String koreanName : machineKoreanName) {
            Machine machine = findByKoreanName(koreanName);
            machines.add(machine);
        }
        return machines;
    }

    public List<Machine> findWithBodyPart(List<String> bodyPartKoreanName) {
        QMachine machine = QMachine.machine;
        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();
        for (String koreanName : bodyPartKoreanName) {
            BodyPart bodyPart = bodyPartRepository.findByKoreanName(koreanName);
            builder.or(QMachine.machine.bodyParts.contains(bodyPart));
        }

        return query
                .select(machine)
                .from(machine)
                .where(builder)
                .orderBy(machine.id.desc())
                .fetch();
    }
}
