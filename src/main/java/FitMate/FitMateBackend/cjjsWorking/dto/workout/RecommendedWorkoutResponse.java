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
    private LocalDate date; //추천 일자
    private String question; //추천 요청 쿼리
    private List<RecommendData> recommends = new ArrayList<>(); //추천 운동

    public RecommendedWorkoutResponse(LocalDate date, String question, List<RecommendedWorkout> recommends) {
        this.date = date;
        this.question = question;
        for (RecommendedWorkout recommend : recommends) {
            this.recommends.add(new RecommendData(recommend.getKoreanName(), recommend.getVideoLink(), recommend.getKorDescription(), recommend.getImgPath()));
        }
    }
}
