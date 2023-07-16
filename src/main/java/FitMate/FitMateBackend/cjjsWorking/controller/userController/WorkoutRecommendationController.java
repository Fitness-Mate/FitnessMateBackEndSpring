package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.chanhaleWorking.service.ChatGptService;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.RecommendData;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutRecommendPageDto;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutRecommendationRepository;
import FitMate.FitMateBackend.cjjsWorking.service.RecommendedWorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutRecommendationService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
public class WorkoutRecommendationController {

    private final WorkoutRecommendationService workoutRecommendationService;
    private final WorkoutService workoutService;
    private final ChatGptService chatGptService;
    private final RecommendedWorkoutService recommendedWorkoutService;
    private final WorkoutRecommendationRepository workoutRecommendationRepository;

    @PostMapping("recommendation/workout")
    public Long getWorkoutRecommendation(@SessionAttribute(name = SessionConst.LOGIN_USER) User user,
                                         @RequestBody WorkoutRecommendationRequest request) throws Exception {
        if(user == null) return null;

        Long recommendationId = workoutRecommendationService.
                createWorkoutRecommendation(user.getId(), request.bodyPartKoreanName, request.machineKoreanName);

        WorkoutRecommendation workoutRecommendation = workoutRecommendationService.findById(recommendationId);
        String question = workoutService.getAllWorkoutToString().concat("\n");
        question = question.concat(workoutRecommendation.getQueryText());
        workoutRecommendationRepository.updateQuery(workoutRecommendation);
        log.info(question);

        chatGptService.sendWorkoutRequest(user.getId(), workoutRecommendation.getId(), question);
        return recommendationId;
    }

    @GetMapping("recommendation/workout/history/list/{page}") //운동 추천 history batch 요청 (TEST 완료)
    public List<WorkoutRecommendPageDto> findRecommendedWorkouts_page(@PathVariable("page") int page,
                                             @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        List<WorkoutRecommendation> findWR = workoutRecommendationService.findAllWithWorkoutRecommendation(page, user.getId());

        return findWR
                .stream().map(WorkoutRecommendPageDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("recommendation/workout/history/{workoutRecommendationId}") //운동 추천 history 단건 요청 (TEST 완료)
    public RecommendedWorkoutResponse findRecommendedWorkout(@PathVariable("workoutRecommendationId") Long workoutRecommendationId,
                                       @SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        List<RecommendedWorkout> findRecommend = recommendedWorkoutService.findById(workoutRecommendationId);
        WorkoutRecommendation wr = workoutRecommendationService.findById(workoutRecommendationId);

        return new RecommendedWorkoutResponse(wr.getDate(), wr.getQueryText(), findRecommend);
    }



    @Data
    static class RecommendedWorkoutResponse {
        private LocalDate date;
        private String question;
        private List<RecommendData> recommends = new ArrayList<>();

        public RecommendedWorkoutResponse(LocalDate date, String question, List<RecommendedWorkout> recommends) {
            this.date = date;
            this.question = question;
            for (RecommendedWorkout recommend : recommends) {
                this.recommends.add(new RecommendData(recommend.getKoreanName(), recommend.getVideoLink(), recommend.getKorDescription()));
            }
        }
    }

    @Data
    static class WorkoutRecommendationRequest {
        private List<String> bodyPartKoreanName;
        private List<String> machineKoreanName;
    }
}
