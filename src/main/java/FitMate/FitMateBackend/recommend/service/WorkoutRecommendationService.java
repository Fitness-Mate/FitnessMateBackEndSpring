package FitMate.FitMateBackend.recommend.service;

import FitMate.FitMateBackend.user.repository.UserRepository;
import FitMate.FitMateBackend.workout.dto.WorkoutRecommendationRequest;
import FitMate.FitMateBackend.recommend.repository.RecommendedWorkoutRepository;
import FitMate.FitMateBackend.recommend.repository.WorkoutRecommendationRepository;
import FitMate.FitMateBackend.workout.repository.BodyPartRepository;
import FitMate.FitMateBackend.workout.repository.MachineRepository;
import FitMate.FitMateBackend.workout.service.WorkoutService;
import FitMate.FitMateBackend.workout.entity.BodyPart;
import FitMate.FitMateBackend.workout.entity.Machine;
import FitMate.FitMateBackend.user.entity.User;
import FitMate.FitMateBackend.workout.entity.Workout;
import FitMate.FitMateBackend.recommend.entity.RecommendedWorkout;
import FitMate.FitMateBackend.recommend.entity.WorkoutRecommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutRecommendationService {

    private final UserRepository userRepository;
    private final WorkoutRecommendationRepository workoutRecommendationRepository;
    private final BodyPartRepository bodyPartRepository;
    private final MachineRepository machineRepository;
    private final WorkoutService workoutService;
    private final RecommendedWorkoutRepository recommendedWorkoutRepository;

    @Transactional
    public Long createWorkoutRecommendation(Long userId, WorkoutRecommendationRequest request) {
        User user = userRepository.findOne(userId);

        List<BodyPart> bodyParts = bodyPartRepository.findByBodyPartKoreanName(request.getBodyPartKoreanName());
        List<Machine> machines = machineRepository.findByMachineKoreanName(request.getMachineKoreanName());

        WorkoutRecommendation workoutRecommendation =
                WorkoutRecommendation.createWorkoutRecommendation
                        (user, bodyParts, machines, workoutService.getAllWorkoutToString(bodyParts, machines));

        workoutRecommendationRepository.save(workoutRecommendation);
        user.addRecommendationHistory(workoutRecommendation);
        return workoutRecommendation.getId();
    }

    @Transactional
    public void updateResponse(Long recommendationId, String response) throws Exception {
        WorkoutRecommendation workoutRecommendation = workoutRecommendationRepository.findById(recommendationId);
        //response가 [ 로 시작하지 않을때에 대한 exception 처리필요

        String[] bodyParts = workoutRecommendation.getRequestedBodyParts().split(",");
        String[] machines = workoutRecommendation.getRequestedMachines().split(",");
        String[] sentences = response.split("\n");

        for (String sentence : sentences) {
            RecommendedWorkout recommendedWorkout = new RecommendedWorkout();

            String[] info = sentence.split("]");
            long workoutId = Long.parseLong(info[0].substring(1));
            String weight = info[1].substring(1).split("kg")[0];
            String repeat = info[2].substring(1);
            String set = info[3].substring(1);

            //find workout
            Workout workout = workoutService.findOne(workoutId);

            //사용자가 요청한 운동부위가 추천받은 운동에 관련된 운동부위에 포함되지 않을 경우 예외처리
            boolean canRecommend = false;
            for (String koreanName : bodyParts) {
                BodyPart findBodyPart = bodyPartRepository.findByKoreanName(koreanName).orElse(null);
                if(workout.getBodyParts().contains(findBodyPart)) {
                    canRecommend= true;
                    break;
                }
            }
            if(!canRecommend) continue;

            //사용자가 요청한 운동기구가 추천받은 운동에 관련된 운동기구에 포함되지 않을 경우 예외처리
            canRecommend = false;
            for (String koreanName : machines) {
                Machine findMachine = machineRepository.findByKoreanName(koreanName).orElse(null);
                if(workout.getMachines().contains(findMachine)) {
                    canRecommend= true;
                    break;
                }
            }
            if(!canRecommend) continue;

            recommendedWorkout.update(
                    workoutRecommendation,
                    workout,
                    weight,
                    repeat,
                    set);
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

    @Transactional
    public void deleteWorkoutRecommendation(Long workoutRecommendationId) {
        WorkoutRecommendation recommendation = this.findById(workoutRecommendationId);
        workoutRecommendationRepository.delete(recommendation);
    }
}