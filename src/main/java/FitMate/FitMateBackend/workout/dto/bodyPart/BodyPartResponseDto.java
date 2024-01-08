package FitMate.FitMateBackend.workout.dto.bodyPart;

import FitMate.FitMateBackend.workout.entity.BodyPart;
import lombok.Data;

@Data
public class BodyPartResponseDto {
    private String englishName;
    private String koreanName;

    public BodyPartResponseDto(BodyPart bodyPart) {
        this.englishName = bodyPart.getEnglishName();
        this.koreanName = bodyPart.getKoreanName();
    }
}
