package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RecommendedWorkoutResponse {
    private LocalDate date;
    private String requestedBodyParts;
    private List<RecommendData> recommends = new ArrayList<>(); //추천 운동


    public RecommendedWorkoutResponse(List<RecommendedWorkout> recommends) {
        for (RecommendedWorkout recommend : recommends) {
            this.recommends.add(new RecommendData(recommend));
        }
        this.date = recommends.get(0).getWorkoutRecommendation().getDate();
        this.requestedBodyParts = recommends.get(0).getWorkoutRecommendation().getRequestedBodyParts();
    }
}
