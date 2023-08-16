package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.UserWorkoutRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutDto;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomException;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("workouts/search/list/{page}") //운동 페이지검색 (TEST 완료)
    public ResponseEntity<List<WorkoutDto>> searchWorkouts_page(@PathVariable("page") int page, @RequestBody UserWorkoutRequest request) {
        return ResponseEntity.ok(
                workoutService.searchAll(page, request).stream()
                .map(WorkoutDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("workouts/{workoutId}") //운동 단일조회 (TEST 완료)
    public ResponseEntity<?> findWorkout(@PathVariable("workoutId") Long workoutId) {
        Workout findWorkout = workoutService.findOne(workoutId);
        if(findWorkout == null)
            throw new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(new WorkoutResponseDto(findWorkout));
    }
}
