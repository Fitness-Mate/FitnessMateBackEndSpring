package FitMate.FitMateBackend.workout.controller;

import FitMate.FitMateBackend.workout.dto.UserWorkoutRequest;
import FitMate.FitMateBackend.workout.dto.WorkoutDto;
import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import FitMate.FitMateBackend.common.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.CustomException;
import FitMate.FitMateBackend.workout.service.WorkoutService;
import FitMate.FitMateBackend.workout.entity.Workout;
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
        System.out.println("sdf");
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
