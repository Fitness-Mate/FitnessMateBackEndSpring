package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.ReadUserInfoResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.RoutineCreateRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.service.RoutineService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myfit")
public class RoutineController {

    private final RoutineService routineService;
    private final WorkoutService workoutService;
    private final UserService userService;

    @GetMapping("/userInfo") //내 정보 요약 - 테스트 완료
    public ResponseEntity<ReadUserInfoResponse> readUserInfo(@RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        User user = userService.getUserWithId(userId);
        if(user == null)
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(new ReadUserInfoResponse(user));
    }

    @PostMapping("/routines/workout")
    public ResponseEntity<String> createRoutine(@RequestHeader HttpHeaders header,
                                                @RequestBody RoutineCreateRequest request) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        User user = userService.getUserWithId(userId);

        return routineService.saveRoutine(user, request.getRoutineName());
    }


    @PostMapping("/routine/{routineId}")
    public void createMyWorkout(@RequestBody MyWorkoutCreateRequest request,
                     @PathVariable("routineId") Long routineId) {
        Workout workout = workoutService.findOne(request.getWorkoutId());
        Routine routine = routineService.findById(routineId);

        MyWorkout myWorkout = new MyWorkout(routine, workout, request);
        routineService.saveMyWorkout(myWorkout);
    }
}
