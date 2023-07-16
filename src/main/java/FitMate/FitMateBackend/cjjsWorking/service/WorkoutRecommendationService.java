package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.*;
import FitMate.FitMateBackend.cjjsWorking.service.apiService.DeepLTranslateService;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import FitMate.FitMateBackend.domain.recommendation.WorkoutRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutRecommendationService {

    private final UserRepository userRepository;
    private final WorkoutRecommendationRepository workoutRecommendationRepository;
    private final BodyPartRepository bodyPartRepository;
    private final MachineRepository machineRepository;
    private final WorkoutRepository workoutRepository;
    private final RecommendedWorkoutRepository recommendedWorkoutRepository;
    private final DeepLTranslateService deepLTranslateService;

    @Transactional
    public Long createWorkoutRecommendation(Long userId, List<String> bodyPartKoreanName,
                                            List<String> machineKoreanName) {
        User user = userRepository.findOne(userId);

        List<BodyPart> bodyParts = bodyPartRepository.findByBodyPartKoreanName(bodyPartKoreanName);
        List<Machine> machines = machineRepository.findByMachineKoreanName(machineKoreanName);

        WorkoutRecommendation workoutRecommendation =
                WorkoutRecommendation.createWorkoutRecommendation
                        (user.getBodyDataHistory().get(0), user, bodyParts, machines);

        workoutRecommendationRepository.save(workoutRecommendation);
        return workoutRecommendation.getId();
    }

    @Transactional
    public void updateResponse(Long userId, Long recommendationId, String response) throws Exception {
        WorkoutRecommendation workoutRecommendation = workoutRecommendationRepository.findById(recommendationId);

        String[] sentences = response.split("\n");
        for (String sentence : sentences) {
            if(sentence.equals("")) continue;

            RecommendedWorkout recommendedWorkout = new RecommendedWorkout();
            int startIdx = sentence.indexOf("<<<") + 3;
            int endIdx = sentence.indexOf(">>>");
            if(endIdx == -1) continue;
            System.out.println(sentence);

            int workoutId = Integer.parseInt(sentence.substring(startIdx, endIdx));

            //find workout
            Workout workout = workoutRepository.findById((long) workoutId);

            //eng, kor description 생성
            String engDescription = sentence.substring(endIdx+4);
            String korDescription = deepLTranslateService.sendRequest(engDescription);

            recommendedWorkout.update(workoutRecommendation, workout.getEnglishName(), workout.getKoreanName(),
                    workout.getVideoLink(), workout.getDescription(), engDescription, korDescription);
            workoutRecommendation.getRws().add(recommendedWorkout);
            recommendedWorkoutRepository.save(recommendedWorkout);
        }
    }

    public WorkoutRecommendation findById(Long recommendationId) {
        return workoutRecommendationRepository.findById(recommendationId);
    }

    public List<WorkoutRecommendation> findAllWithWorkoutRecommendation(int page, Long userId) {
        return workoutRecommendationRepository.findAllWithWorkoutRecommendation(page, userId);
    }
}