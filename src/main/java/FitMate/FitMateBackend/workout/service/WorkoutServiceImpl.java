package FitMate.FitMateBackend.workout.service;

import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.repository.MachineRepository;
import FitMate.FitMateBackend.workout.repository.WorkoutRepository;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.workout.entity.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MachineRepository machineRepository;
    private final BodyPartService bodyPartService;
    private final S3FileService s3FileService;

    @Override
    @Transactional
    public WorkoutResponseDto save(WorkoutForm form) {
        if(!this.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            throw new CustomException(CustomErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);

        Workout workout = new Workout();
        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage());
            workout.update(form, fileName);
        } else {
            workout.update(form, ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME);
        }

        //workout과 연관된 bodyPart연결
        for (String bodyPartKoreanName : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(bodyPartKoreanName);
            workout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(workout);
        }

        //workout과 연관된 machine연결
        for (String machineKoreanName : form.getMachineKoreanName()) {
             Machine findMachine = machineRepository.findByKoreanName(machineKoreanName)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION));
             workout.getMachines().add(findMachine);
             findMachine.addWorkout(workout);
        }

        workoutRepository.save(workout);

        return new WorkoutResponseDto(workout);
    }

    @Override
    @Transactional
    public WorkoutResponseDto update(WorkoutForm form, Long id) {
        if(!this.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            throw new CustomException(CustomErrorCode.WORKOUT_ALREADY_EXIST_EXCEPTION);

        Workout findWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));

        if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME)) //기존 이미지 삭제
            s3FileService.deleteImage(ServiceConst.S3_DIR_WORKOUT,findWorkout.getImgFileName());

        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage()); //s3에 이미지 추가
            findWorkout.update(form, fileName);
        } else {
            findWorkout.update(form, ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME);
        }

        //workout <-> bodyPart 연결 해제
        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        findWorkout.getBodyParts().clear();

        //workout과 연관된 bodyPart 연결
        for (String name : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(name);
            findWorkout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(findWorkout);
        }

        //workout <-> machine 연결 해제
        for (Machine machine : findWorkout.getMachines()) {
            machine.removeWorkout(findWorkout);
        }
        findWorkout.getMachines().clear();

        //workout과 연관된 machine 연결
        for (String machineKoreanName : form.getMachineKoreanName()) {
            Machine findMachine = machineRepository.findByKoreanName(machineKoreanName)
                    .orElseThrow(() -> new CustomException(CustomErrorCode.MACHINE_NOT_FOUND_EXCEPTION));
            findWorkout.getMachines().add(findMachine);
            findMachine.addWorkout(findWorkout);
        }

        return new WorkoutResponseDto(findWorkout);
    }

    @Override
    public WorkoutResponseDto findById(Long id) {
        Workout workout = workoutRepository.findById(id)
            .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));
        return new WorkoutResponseDto(workout);
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
                .map(WorkoutResponseDto::new)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public WorkoutResponseDto remove(Long id) {
        Workout findWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomErrorCode.WORKOUT_NOT_FOUND_EXCEPTION));

        //workout과 연관된 bodyPart제거
        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }
        //workout과 연관된 machine제거
        for (Machine machine : findWorkout.getMachines()) {
            machine.removeWorkout(findWorkout);
        }
        if(findWorkout.getImgFileName() != null) {
            //workout 이미지 삭제
            if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_WORKOUT_IMAGE_NAME))
                s3FileService.deleteImage(ServiceConst.S3_DIR_WORKOUT, findWorkout.getImgFileName());
        }

        workoutRepository.remove(findWorkout);
        return new WorkoutResponseDto(findWorkout);
    }

    @Override
    public List<WorkoutResponseDto> searchAll(int page, WorkoutSearchCond cond) {
        return workoutRepository.searchAll(page, cond)
            .stream().map(WorkoutResponseDto::new).toList();
    }

    public String getAllWorkoutToString(List<BodyPart> bodyParts, List<Machine> machines) {
        List<Workout> workouts = workoutRepository.findAllWithBodyPartsAndMachines(bodyParts, machines);

        String str = "list: [ ";
        for (int i = 0; i < workouts.size(); i++) {

            if(i == (workouts.size()-1)) str += " " + workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + ")";
            else str += workouts.get(i).getEnglishName() + "(" + workouts.get(i).getId() + "), ";
        }
        str += " ]";

        return str;
    }
}