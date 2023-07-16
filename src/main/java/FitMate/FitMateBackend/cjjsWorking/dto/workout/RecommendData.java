package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import lombok.Data;

@Data
public class RecommendData {
    private String workoutName;
    private String videoLink;
    private String description;

    public RecommendData(String workoutName, String videoLink, String description) {
        this.workoutName = workoutName;
        this.videoLink = videoLink;
        this.description = description;
    }
}
