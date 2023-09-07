package FitMate.FitMateBackend.cjjsWorking.service.recommendService;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.RecommendErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.RecommendException;
import FitMate.FitMateBackend.cjjsWorking.repository.recommend.RecommendedWorkoutRepository;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        List<RecommendedWorkout> recommendedWorkouts = recommendedWorkoutRepository.findById(recommendationId);
        if(recommendedWorkouts.isEmpty())
            throw new RecommendException(RecommendErrorCode.RECOMMEND_NOT_FOUND_EXCEPTION, 404);

        return recommendedWorkouts;
    }
}
