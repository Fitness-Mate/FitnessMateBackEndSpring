package FitMate.FitMateBackend.cjjsWorking.dto.Machine;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetMachineResponse {
    private String englishName;
    private String koreanName;
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public GetMachineResponse(String englishName, String koreanName, List<BodyPart> bodyParts) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        for (BodyPart bodyPart : bodyParts) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}