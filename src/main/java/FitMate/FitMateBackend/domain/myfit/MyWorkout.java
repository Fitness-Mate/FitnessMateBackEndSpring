package FitMate.FitMateBackend.domain.myfit;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.Workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyWorkout extends MyFit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private String weight;
    private String rep;
    private String setCount;

    public MyWorkout(Routine routine, Workout workout,
                     MyWorkoutCreateRequest request, int myFitIndex) {
        super(routine, myFitIndex);
        this.workout = workout;
        this.weight = request.getWeight();
        this.rep = request.getRep();
        this.setCount = request.getSetCount();
    }
}