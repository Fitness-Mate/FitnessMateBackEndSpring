package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutReadAllResponse;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.service.MyFitService;
import FitMate.FitMateBackend.cjjsWorking.service.RoutineService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myfit")
public class MyFitController {

    private final MyFitService myFitService;
    private final WorkoutService workoutService;
    private final RoutineService routineService;

    @PostMapping("/routines/workout/{routineId}") //루틴에 운동 추가 - 테스트 완료
    public void createMyWorkout(@RequestBody MyWorkoutCreateRequest request,
                                @PathVariable("routineId") Long routineId) {
        List<MyWorkout> myWorkouts = myFitService.findAllMyWorkoutWithRoutineId(routineId);
        if(myWorkouts.size() >= ServiceConst.MY_WORKOUT_MAX_SIZE)
            throw new CustomException(CustomErrorCode.MY_WORKOUT_SIZE_OVER_EXCEPTION);

        Workout workout = workoutService.findOne(request.getWorkoutId());
        Routine routine = routineService.findRoutineById(routineId);

        MyFit myWorkout = new MyWorkout(routine, workout, request, myWorkouts.size()+1);
        myFitService.saveMyFit(myWorkout);
    }

    @GetMapping("/routines/workout/{routineId}") //루틴에 속한 운동 리스트 조회 - 테스트 완료
    public List<MyWorkoutReadAllResponse> readMyWorkoutInRoutine(@PathVariable("routineId") Long routineId) {
        return myFitService.findAllMyWorkoutWithRoutineId(routineId).stream()
                .map(MyWorkoutReadAllResponse::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/routines/workout/{myFitId}")
    public void updateMyWorkout(@PathVariable("myFitId") Long myWorkoutId) {
        //myworkout index, weight, rep, setCount 변경 가능
        myFitService.updateMyWorkout(myWorkoutId);
    }

    @DeleteMapping("/routines/workout/{myFitId}") //루틴에 속한 운동 삭제 - 테스트 완료
    public void deleteMyWorkout(@PathVariable("myFitId") Long myWorkoutId) {
        myFitService.deleteMyWorkout(myWorkoutId);
    }
}
