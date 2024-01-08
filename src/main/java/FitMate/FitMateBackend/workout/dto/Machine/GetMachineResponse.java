package FitMate.FitMateBackend.workout.dto.Machine;

import FitMate.FitMateBackend.workout.entity.BodyPart;
import FitMate.FitMateBackend.workout.entity.Machine;
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

    public GetMachineResponse(Machine findMachine) {
        this.englishName = findMachine.getEnglishName();
        this.koreanName = findMachine.getKoreanName();
        for (BodyPart bodyPart : findMachine.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}