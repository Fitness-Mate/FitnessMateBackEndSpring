package FitMate.FitMateBackend.cjjsWorking.dto.bodyPart;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllBodyPartResponseDto {
    private List<BodyPartName> bodyPartKoreanName = new ArrayList<>();

    public AllBodyPartResponseDto(List<BodyPart> bodyParts) {
        for (BodyPart bodyPart : bodyParts) {
            bodyPartKoreanName.add(new BodyPartName(bodyPart.getEnglishName(), bodyPart.getKoreanName()));
        }
    }
}

@Data
@AllArgsConstructor
class BodyPartName {
    private String englishName;
    private String koreanName;
}
