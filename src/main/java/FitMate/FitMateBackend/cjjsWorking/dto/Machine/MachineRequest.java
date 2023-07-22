package FitMate.FitMateBackend.cjjsWorking.dto.Machine;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MachineRequest {
    private String englishName;
    private String koreanName;
    private List<String> bodyPartKoreanName;
}