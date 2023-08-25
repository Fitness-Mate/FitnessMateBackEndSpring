package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.controller.userController.WorkoutRecommendationController;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutRecommendationRequest;
import FitMate.FitMateBackend.cjjsWorking.repository.*;
import FitMate.FitMateBackend.cjjsWorking.service.apiService.DeepLTranslateService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
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
    private final WorkoutService workoutService;
    private final RecommendedWorkoutRepository recommendedWorkoutRepository;
    private final DeepLTranslateService deepLTranslateService;

    @Transactional
    public Long createWorkoutRecommendation(Long userId, WorkoutRecommendationRequest request) {
        User user = userRepository.findOne(userId);

        List<BodyPart> bodyParts = bodyPartRepository.findByBodyPartKoreanName(request.getBodyPartKoreanName());
        List<Machine> machines = machineRepository.findByMachineKoreanName(request.getMachineKoreanName());

        WorkoutRecommendation workoutRecommendation =
                WorkoutRecommendation.createWorkoutRecommendation
                        (user, bodyParts, machines, workoutService.getAllWorkoutToString());

        workoutRecommendationRepository.save(workoutRecommendation);
        return workoutRecommendation.getId();
    }

    @Transactional
    public void updateResponse(Long userId, Long recommendationId, String response) throws Exception {
        WorkoutRecommendation workoutRecommendation = workoutRecommendationRepository.findById(recommendationId);
        //response가 [ 로 시작하지 않을때에 대한 exception 처리필요

        String[] sentences = response.split("\n");
        for (String sentence : sentences) {
            RecommendedWorkout recommendedWorkout = new RecommendedWorkout();
            String[] info = sentence.split("]");
            long workoutId = Long.parseLong(info[0].substring(1));
            String weight = info[1].substring(1);
            String repeat = info[2].substring(1);
            String set = info[3].substring(1);

            //find workout
            Workout workout = workoutRepository.findById(workoutId).orElse(null);
            System.out.println("====================================");
            System.out.println(workout.getKoreanName());
            System.out.println("====================================");

            if(workout == null) return;

//            //eng, kor description 생성
//            String engDescription = sentence.substring(endIdx+4);
//            String korDescription = deepLTranslateService.sendRequest(engDescription);
            String accessURL = S3FileService.getAccessURL(ServiceConst.S3_DIR_WORKOUT, workout.getImgFileName());
//
            recommendedWorkout.update(workoutRecommendation, workout.getEnglishName(), workout.getKoreanName(),
                    accessURL, workout.getDescription(), weight, repeat, set);
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