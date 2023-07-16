package FitMate.FitMateBackend.cjjsWorking.dto.bodyPart;

import lombok.Data;

@Data
public class BodyPartResponseDto {
    private String englishName;
    private String koreanName;

    public BodyPartResponseDto(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }
}
