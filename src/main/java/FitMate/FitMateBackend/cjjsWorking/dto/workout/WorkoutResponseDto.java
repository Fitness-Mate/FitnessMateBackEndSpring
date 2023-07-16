package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutResponseDto {
    private String englishName;
    private String koreanName;
    private String videoLink;
    private String description;
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public WorkoutResponseDto(String englishName, String koreanName, String videoLink, String description, List<BodyPart> bodyParts) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.videoLink = videoLink;
        this.description = description;
        for (BodyPart bodyPart : bodyParts) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}
