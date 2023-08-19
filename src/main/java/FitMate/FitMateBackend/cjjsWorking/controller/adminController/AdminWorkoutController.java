package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("admin/workouts")//운동 등록 (TEST 완료)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> saveWorkout(@ModelAttribute WorkoutForm form) {
        return workoutService.saveWorkout(form);
    }

    @GetMapping("admin/workouts/{workoutId}") //운동 단일조회 (TEST 완료)
    public ResponseEntity<?> findWorkout(@PathVariable("workoutId") Long workoutId) {
        Workout findWorkout = workoutService.findOne(workoutId);
        if(findWorkout == null)
            throw new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(new WorkoutResponseDto(findWorkout));
    }

    @GetMapping("admin/workouts/list/{page}") //운동 페이지조회 (TEST 완료)
    public ResponseEntity<?> findWorkouts_page(@PathVariable("page") int page) {
        return workoutService.findAll(page);
    }

    @PutMapping("admin/workouts/{workoutId}") //운동 수정 (TEST 완료)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> updateWorkout(@PathVariable("workoutId") Long workoutId, @ModelAttribute WorkoutForm form) {
        return workoutService.updateWorkout(workoutId, form);
    }

    @DeleteMapping("admin/workouts/{workoutId}") //운동 삭제 (TEST 완료)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> deleteWorkout(@PathVariable("workoutId") Long workoutId) {
        return workoutService.removeWorkout(workoutId);
    }
}