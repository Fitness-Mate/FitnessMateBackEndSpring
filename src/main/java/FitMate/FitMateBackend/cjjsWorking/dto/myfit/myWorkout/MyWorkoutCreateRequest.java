package FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MyWorkoutCreateRequest {
    private List<Long> workoutIds = new ArrayList<>();
    private String weight;
    private String rep;
    private String setCount;
}