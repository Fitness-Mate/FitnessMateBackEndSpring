package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.chanhaleWorking.service.FileStoreService;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.UserWorkoutRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutDto;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutSearch;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.domain.Workout;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserWorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("workouts/search/list/{page}") //batch 검색 (TEST 완료)
    public List<WorkoutDto> searchWorkouts_page(@PathVariable("page") int page,
                                                @RequestBody UserWorkoutRequest request) {
        //비회원도 운동 검색은 할 수 있기 때문에 user session 제약 없앰
        WorkoutSearch search = new WorkoutSearch(request.getSearchKeyword(), request.getBodyPartKoreanName());
        List<Workout> searchWorkouts = workoutService.searchAll(page, search);

        return searchWorkouts.stream()
                .map(WorkoutDto::new)
                .collect(Collectors.toList());
    }

//    @GetMapping("workouts/{workoutId}") //단일조회 (TEST 완료)
//    public WorkoutResponseDto findWorkout(@PathVariable("workoutId") Long workoutId) {
//        //비회원도 운동 검색은 할 수 있기 때문에 user session 제약 없앰
//        Workout findWorkout = workoutService.findOne(workoutId);
//        return new WorkoutResponseDto(findWorkout);
//    }
}
