package FitMate.FitMateBackend.workout.controller;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import FitMate.FitMateBackend.workout.service.WorkoutServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutServiceImpl workoutService;

    @PostMapping("") //운동 등록
    public ResponseEntity<WorkoutResponseDto> saveWorkout(
        @ModelAttribute WorkoutForm form
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(workoutService.save(form));
    }

    @PostMapping("/search/list/{page}") //운동 페이지 검색
    public ResponseEntity<List<WorkoutResponseDto>> search(
        @PathVariable("page") int page,
        @RequestBody WorkoutSearchCond cond
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.searchAll(page, cond));
    }

    @GetMapping("/{workoutId}") //운동 단건조회
    public ResponseEntity<WorkoutResponseDto> findOne(
        @PathVariable("workoutId") Long workoutId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.findById(workoutId));
    }

    @PutMapping("/{workoutId}") //운동 수정
    public ResponseEntity<WorkoutResponseDto> updateWorkout(
        @PathVariable("workoutId") Long workoutId,
        @ModelAttribute WorkoutForm form
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.update(form, workoutId));
    }

    @DeleteMapping("/{workoutId}") //운동 삭제
    public ResponseEntity<WorkoutResponseDto> deleteWorkout(
        @PathVariable("workoutId") Long workoutId
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(workoutService.remove(workoutId));
    }
}
