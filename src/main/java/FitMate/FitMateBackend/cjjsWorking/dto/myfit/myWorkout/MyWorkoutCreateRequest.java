package FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyWorkoutCreateRequest {
    private Long workoutId;
    private String weight;
    private String rep;
    private String setCount;
}