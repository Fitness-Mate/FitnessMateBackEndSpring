package FitMate.FitMateBackend.cjjsWorking.dto.Machine;

import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MachineDto {
    private Long id;
    private String englishName;
    private String koreanName;
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public MachineDto(Machine machine) {
        this.id = machine.getId();
        this.englishName = machine.getEnglishName();
        this.koreanName = machine.getKoreanName();
        for (BodyPart bodyPart : machine.getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}