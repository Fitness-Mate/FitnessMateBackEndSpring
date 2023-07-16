package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.RecommendedWorkoutRepository;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendedWorkoutService {

    private final RecommendedWorkoutRepository recommendedWorkoutRepository;

    @Transactional
    public void save(RecommendedWorkout recommendedWorkout) {
        recommendedWorkoutRepository.save(recommendedWorkout);
    }

    public List<RecommendedWorkout> findById(Long recommendationId) {
        return recommendedWorkoutRepository.findById(recommendationId);
    }
}
