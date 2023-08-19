package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import lombok.Data;

@Data
public class RecommendData {
    private String workoutName;
    private String videoLink;
    private String description;
    private String imgPath;

    public RecommendData(String workoutName, String videoLink, String description, String imgPath) {
        this.workoutName = workoutName;
        this.videoLink = videoLink;
        this.description = description;
        this.imgPath = imgPath;
    }
}
