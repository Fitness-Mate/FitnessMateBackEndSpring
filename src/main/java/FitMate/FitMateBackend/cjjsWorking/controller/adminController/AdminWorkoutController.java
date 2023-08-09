package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutDto;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminWorkoutController {

    private final WorkoutService workoutService;
    private final BodyPartService bodyPartService;
    private final S3FileService s3FileService;

    @PostMapping("admin/workouts")// (TEST 완료)
    public String saveWorkout(@SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                            @ModelAttribute WorkoutForm form) {
        if(admin == null) return null;
        if(!workoutService.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            return "이미 존재하는 운동입니다. 이름을 확인해주세요.";

        Workout workout = new Workout();
        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage());
            workout.update(form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), fileName);
        } else {
            workout.update(form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), ServiceConst.DEFAULT_IMAGE_NAME);
        }

        for (String koreanName : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(koreanName);
            workout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(workout);
        }

        return workoutService.saveWorkout(workout).toString();
    }

    @PutMapping("admin/workouts/{workoutId}") //운동 정보 수정 (TEST 완료)
    public String updateWorkout(@PathVariable("workoutId") Long workoutId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                              @ModelAttribute WorkoutForm form) {
        if(admin == null) return null;
        if(!workoutService.checkWorkoutNameDuplicate(form.getKoreanName(), form.getEnglishName()))
            return "이미 존재하는 운동입니다. 이름을 확인해주세요.";

        Workout findWorkout = workoutService.findOne(workoutId);
        if(!findWorkout.getImgFileName().equals(ServiceConst.DEFAULT_IMAGE_NAME)) //기존 이미지 삭제
            s3FileService.deleteImage(ServiceConst.S3_DIR_WORKOUT,findWorkout.getImgFileName());

        if(!form.getImage().isEmpty()) {
            String fileName = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage()); //s3에 이미지 추가
            return workoutService.updateWorkout(workoutId, form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), form.getBodyPartKoreanName(), fileName);
        } else {
            return workoutService.updateWorkout(workoutId, form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), form.getBodyPartKoreanName(), ServiceConst.DEFAULT_IMAGE_NAME);
        }
    }

    /* 사용 안함
    @GetMapping("admin/workouts/image/{workoutId}") //이미지조회 (TEST 완료)
    public ResponseEntity<Resource> findWorkoutImage(@PathVariable("workoutId") Long workoutId,
                                                    @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) throws MalformedURLException {
        if(admin == null) return null;
        //삭제 가능

        Workout findWorkout = workoutService.findOne(workoutId);
        UrlResource imgRrc = new UrlResource("file:" + FileStoreService.getFullPath(findWorkout.getImagePath()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(imgRrc);
    }
    */

    @GetMapping("admin/workouts/{workoutId}") //단건 조회 (TEST 완료)
    public WorkoutResponseDto findWorkout(@PathVariable("workoutId") Long workoutId,
                                          @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        Workout findWorkout = workoutService.findOne(workoutId);
        return new WorkoutResponseDto(findWorkout.getEnglishName(), findWorkout.getKoreanName(), findWorkout.getImgFileName(), findWorkout.getVideoLink(),
                findWorkout.getDescription(), findWorkout.getBodyParts());
    }

    @GetMapping("admin/workouts/list/{page}") //batch 단위 조회 (TEST 완료)
    public List<WorkoutDto> findWorkouts_page(@PathVariable("page") int page,
                                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        List<Workout> findWorkouts = workoutService.findAll(page);
        return findWorkouts.stream()
                .map(WorkoutDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("admin/workouts/{workoutId}") //운동 정보 삭제 (TEST 완료)
    public String deleteWorkout(@PathVariable("workoutId") Long workoutId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        workoutService.removeWorkout(workoutId);
        return workoutId.toString();
    }
}