package FitMate.FitMateBackend.recommend.controller;

import FitMate.FitMateBackend.common.util.tmpUtil.ChatGptService;
import FitMate.FitMateBackend.workout.dto.RecommendedWorkoutResponse;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendPageDto;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendationRequest;
import FitMate.FitMateBackend.recommend.service.RecommendedWorkoutService;
import FitMate.FitMateBackend.recommend.service.WorkoutRecommendationService;
import FitMate.FitMateBackend.common.util.authService.JwtService;
import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import FitMate.FitMateBackend.recommend.entity.WorkoutRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
public class WorkoutRecommendationController {

    private final WorkoutRecommendationService workoutRecommendationService;
    private final ChatGptService chatGptService;
    private final RecommendedWorkoutService recommendedWorkoutService;

    @PostMapping("recommendation/workout") //운동 추천 요청
    public Long getWorkoutRecommendation(@RequestHeader HttpHeaders header, @RequestBody WorkoutRecommendationRequest request) throws Exception {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        Long recommendationId = workoutRecommendationService.
                createWorkoutRecommendation(userId, request);

        WorkoutRecommendation workoutRecommendation = workoutRecommendationService.findById(recommendationId);
        String question = workoutRecommendation.getQueryText();

        log.info(question);
        chatGptService.sendWorkoutRequest(workoutRecommendation.getId(), question);
        return recommendationId;
    }

    @GetMapping("recommendation/workout/history/list/{page}") //운동 추천 history batch 요청
    public List<WorkoutRecommendPageDto> findRecommendedWorkouts_page(@RequestHeader HttpHeaders header,
                                                                      @PathVariable("page") int page) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        List<WorkoutRecommendation> findWR = workoutRecommendationService.findAllWithWorkoutRecommendation(page, userId);

        return findWR
                .stream().map(WorkoutRecommendPageDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("recommendation/workout/history/{workoutRecommendationId}") //운동 추천 history 단건 요청
    public RecommendedWorkoutResponse findRecommendedWorkout(@PathVariable("workoutRecommendationId") Long workoutRecommendationId) {
        List<RecommendedWorkout> findRecommend = recommendedWorkoutService.findById(workoutRecommendationId);
        return new RecommendedWorkoutResponse(findRecommend);
    }

    @DeleteMapping("recommendation/workout/history/{workoutRecommendationId}") //운동 추천 history 삭제
    public void deleteWorkoutRecommendation(@PathVariable("workoutRecommendationId") Long workoutRecommendationId) {
        workoutRecommendationService.deleteWorkoutRecommendation(workoutRecommendationId);
    }
}