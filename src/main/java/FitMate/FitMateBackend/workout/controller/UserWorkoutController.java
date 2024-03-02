package FitMate.FitMateBackend.workout.controller;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import FitMate.FitMateBackend.workout.service.WorkoutServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class UserWorkoutController {

    private final WorkoutServiceImpl workoutService;

    //운동 검색
    @PostMapping("/search/list/{page}")
    public ResponseEntity<List<WorkoutResponseDto>> search(
        @PathVariable("page") int page,
        @RequestBody WorkoutSearchCond cond
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            workoutService.searchAll(page, cond)
        );
    }

    //운동 단건 조회
    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> findWorkout(
        @PathVariable("workoutId") Long workoutId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            workoutService.findById(workoutId)
        );
    }
}
