package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.cjjsWorking.service.RoutineService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;
    private final WorkoutService workoutService;

    @PostMapping("/routine/{routineId}")
    public void createMyWorkout(@RequestBody MyWorkoutCreateRequest request,
                     @PathVariable("routineId") Long routineId) {
        Workout workout = workoutService.findOne(request.getWorkoutId());
        Routine routine = routineService.findById(routineId);

        MyWorkout myWorkout = new MyWorkout(routine, workout, request);
        routineService.saveMyWorkout(myWorkout);
    }
}
