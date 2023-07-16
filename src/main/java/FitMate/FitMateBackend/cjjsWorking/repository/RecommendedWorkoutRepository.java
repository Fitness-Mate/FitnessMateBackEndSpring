package FitMate.FitMateBackend.cjjsWorking.repository;

import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecommendedWorkoutRepository {

    private final EntityManager em;

    public void save(RecommendedWorkout recommendedWorkout) {
        em.persist(recommendedWorkout);
    }

    public List<RecommendedWorkout> findById(Long recommendationId) {
        return em.createQuery("select rw from RecommendedWorkout rw" +
                " join fetch rw.workoutRecommendation wr" +
                " where wr.id = :id", RecommendedWorkout.class)
                .setParameter("id", recommendationId)
                .getResultList();
    }
}
