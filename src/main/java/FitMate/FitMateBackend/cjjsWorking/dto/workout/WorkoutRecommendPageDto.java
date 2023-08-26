package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WorkoutRecommendPageDto {
    private Long recommendId;
    private LocalDate date;
    private List<RecommendData> workouts = new ArrayList<>();

    public WorkoutRecommendPageDto(WorkoutRecommendation workoutRecommendation) {
        this.recommendId = workoutRecommendation.getId();
        this.date = workoutRecommendation.getDate();
        for (RecommendedWorkout recommend : workoutRecommendation.getRws()) {
            workouts.add(new RecommendData(recommend));
        }
    }
}