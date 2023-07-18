package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutSearch;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final BodyPartRepository bodyPartRepository;

    @Transactional
    public Long saveWorkout(Workout workout) {
        workoutRepository.save(workout);
        return workout.getId();
    }

    @Transactional
    public Long updateWorkout(Long workoutId, String englishName, String koreanName, String videoLink,
                              String description, List<String> bodyPartKoreanName, String imagePath) {
        Workout findWorkout = workoutRepository.findById(workoutId);
        findWorkout.update(englishName, koreanName, videoLink, description, imagePath);

        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        findWorkout.getBodyParts().clear();

        for (String name : bodyPartKoreanName) {
            BodyPart findBodyPart = bodyPartRepository.findByKoreanName(name);
            findBodyPart.addWorkout(findWorkout);
            findWorkout.getBodyParts().add(findBodyPart);
        }

        return workoutId;
    }

    public Workout findOne(Long workoutId) {
        return workoutRepository.findById(workoutId);
    }
    public Workout findByWorkoutKoreanName(String workoutName) {
        return workoutRepository.findByKoreanName(workoutName).orElse(null);
    }
    public Workout findByWorkoutEnglishName(String englishName) {
        return workoutRepository.findByEnglishName(englishName).orElse(null);
    }

    public List<Workout> findAll(int page) {
        return workoutRepository.findAll(page);
    }

    @Transactional
    public Long removeWorkout(Long workoutId) {
        Workout findWorkout = workoutRepository.findById(workoutId);
        workoutRepository.remove(findWorkout);
        return workoutId;
    }

    /////////////////////////////////////////////////////////////////

    public List<Workout> searchAll(int page, WorkoutSearch search) {
        return workoutRepository.searchAll(page, search);
    }

    public String getAllWorkoutToString() {
        List<Workout> workouts = workoutRepository.findAll();
        String str = "list: [ ";
        for (int i = 0; i < workouts.size(); i++) {
            if(i == (workouts.size()-1)) str += " " + workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + ")";
            else str += workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + "), ";
        }
        str += " ]";

        return str;
    }
}