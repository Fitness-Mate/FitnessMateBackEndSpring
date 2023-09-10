package FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSetData {
    private Long routineId;
    private int routineIndex;
    private String routineName;
}
