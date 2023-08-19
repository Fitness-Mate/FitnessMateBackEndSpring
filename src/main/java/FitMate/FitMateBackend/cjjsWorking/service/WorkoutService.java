package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.UserWorkoutRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutDto;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutSearch;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final BodyPartService bodyPartService;
    private final S3FileService s3FileService;

    @Transactional
    public ResponseEntity<String> saveWorkout(WorkoutForm form) {
        if(!this.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            throw new CustomException(CustomErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);

        Workout workout = new Workout();

        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage());
            workout.update(form, fileName);
        } else {
            workout.update(form, ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME);
        }

        for (String koreanName : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(koreanName);
            workout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(workout);
        }

        workoutRepository.save(workout);
        return ResponseEntity.ok("[" + workout.getKoreanName() + ":" + workout.getEnglishName() + "] 등록 완료");
    }

    @Transactional
    public ResponseEntity<String> updateWorkout(Long workoutId, WorkoutForm form) {
        if(!this.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            throw new CustomException(CustomErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);

        Workout findWorkout = workoutRepository.findById(workoutId).orElse(null);
        if(findWorkout == null)
            throw new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION);

        if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME)) //기존 이미지 삭제
            s3FileService.deleteImage(ServiceConst.S3_DIR_WORKOUT,findWorkout.getImgFileName());

        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage()); //s3에 이미지 추가
            findWorkout.update(form, fileName);
        } else {
            findWorkout.update(form, ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME);
        }

        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        findWorkout.getBodyParts().clear();

        for (String name : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(name);
            findBodyPart.addWorkout(findWorkout);
            findWorkout.getBodyParts().add(findBodyPart);
        }

        return ResponseEntity.ok("[" + findWorkout.getKoreanName() + ":" + findWorkout.getEnglishName() + "] 수정 완료");
    }

    public Workout findOne(Long workoutId) {
        return workoutRepository.findById(workoutId).orElse(null);
    }

    public boolean checkWorkoutNameDuplicate(String koreanName, String englishName) {
        Workout w1 = workoutRepository.findByKoreanName(koreanName).orElse(null);
        Workout w2 = workoutRepository.findByEnglishName(englishName).orElse(null);
        return (w1 == null && w2 == null);
    }

    public ResponseEntity<?> findAll(int page) {
        List<Workout> findWorkouts = workoutRepository.findAll(page);
        if(findWorkouts.isEmpty())
            throw new CustomException(CustomErrorCode.PAGE_NOT_FOUND_EXCEPTION);

        return ResponseEntity.ok(
                findWorkouts.stream()
                .map(WorkoutDto::new)
                .collect(Collectors.toList()));
    }

    @Transactional
    public ResponseEntity<String> removeWorkout(Long workoutId) {
        Workout findWorkout = workoutRepository.findById(workoutId).orElse(null);
        if(findWorkout == null)
            throw new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION);

        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }

        if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME))
            s3FileService.deleteImage(ServiceConst.S3_DIR_WORKOUT, findWorkout.getImgFileName());

        workoutRepository.remove(findWorkout);
        return ResponseEntity.ok("[" + findWorkout.getKoreanName() + ":" + findWorkout.getEnglishName() + "] 삭제 완료");
    }

    /////////////////////////////////////////////////////////////////

    public List<Workout> searchAll(int page, UserWorkoutRequest request) {
        WorkoutSearch search = new WorkoutSearch(request.getSearchKeyword(), request.getBodyPartKoreanName());
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