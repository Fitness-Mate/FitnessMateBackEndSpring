package FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSetRequest {
    List<RoutineSetData> routines = new ArrayList<>();
}