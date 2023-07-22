package FitMate.FitMateBackend.cjjsWorking.dto.bodyPart;

import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class GetAllBodyPartResponse {
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public GetAllBodyPartResponse(List<BodyPart> bodyParts) {
        for (BodyPart bodyPart : bodyParts) {
            bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}