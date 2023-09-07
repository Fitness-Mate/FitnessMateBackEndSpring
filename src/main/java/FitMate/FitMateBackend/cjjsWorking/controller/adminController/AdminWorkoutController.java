package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/workouts")//운동 등록 (TEST 완료)
    public ResponseEntity<String> saveWorkout(@ModelAttribute WorkoutForm form) {
        return workoutService.saveWorkout(form);
    }

    @GetMapping("/workouts/{workoutId}") //운동 단일조회 (TEST 완료)
    public ResponseEntity<?> findWorkout(@PathVariable("workoutId") Long workoutId) {
        return ResponseEntity.ok(new WorkoutResponseDto(workoutService.findOne(workoutId)));
    }

    @GetMapping("/workouts/list/{page}") //운동 페이지조회 (TEST 완료)
    public ResponseEntity<?> findWorkouts_page(@PathVariable("page") int page) {
        return workoutService.findAll(page);
    }

    @PutMapping("/workouts/{workoutId}") //운동 수정 (TEST 완료)
    public ResponseEntity<String> updateWorkout(@PathVariable("workoutId") Long workoutId, @ModelAttribute WorkoutForm form) {
        return workoutService.updateWorkout(workoutId, form);
    }

    @DeleteMapping("/workouts/{workoutId}") //운동 삭제 (TEST 완료)
    public ResponseEntity<String> deleteWorkout(@PathVariable("workoutId") Long workoutId) {
        return workoutService.removeWorkout(workoutId);
    }
}