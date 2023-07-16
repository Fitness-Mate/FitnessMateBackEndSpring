package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.chanhaleWorking.service.FileStoreService;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutDto;
import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutResponseDto;
import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminWorkoutController {

    private final WorkoutService workoutService;
    private final BodyPartRepository bodyPartRepository;

    @PostMapping("admin/workouts")// (TEST 완료)
    public Long saveWorkout(@SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                            @ModelAttribute WorkoutForm form) throws IOException {
        if(admin == null) return null;

        Workout workout = new Workout();
        if(!form.getImage().isEmpty()) {
            String imagePath = FileStoreService.storeFile(form.getImage());
            workout.update(form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), imagePath);
        } else {
            workout.update(form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), ServiceConst.DEFAULT_IMAGE_PATH);
        }

        for (String koreanName : form.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartRepository.findByKoreanName(koreanName);
            workout.getBodyParts().add(findBodyPart);
            findBodyPart.addWorkout(workout);
        }

        Long workoutId = workoutService.saveWorkout(workout);
        return workoutId;
    }

    @PutMapping("admin/workouts/{workoutId}") //운동 정보 수정 (TEST 완료)
    public Long updateWorkout(@PathVariable("workoutId") Long workoutId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                              @ModelAttribute WorkoutForm form) throws IOException {
        if(admin == null) return null;

        if(!form.getImage().isEmpty()) {
            String imagePath = FileStoreService.storeFile(form.getImage());
            workoutService.updateWorkout(workoutId, form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), form.getBodyPartKoreanName(), imagePath);
        } else {
            workoutService.updateWorkout(workoutId, form.getEnglishName(), form.getKoreanName(), form.getVideoLink(),
                    form.getDescription(), form.getBodyPartKoreanName(), ServiceConst.DEFAULT_IMAGE_PATH);
        }

        return workoutId;
    }

    @GetMapping("admin/workouts/image/{workoutId}") //이미지조회 (TEST 완료)
    public ResponseEntity<Resource> findWorkoutImage(@PathVariable("workoutId") Long workoutId,
                                                    @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) throws MalformedURLException {
        if(admin == null) return null;

        Workout findWorkout = workoutService.findOne(workoutId);
        UrlResource imgRrc = new UrlResource("file:" + FileStoreService.getFullPath(findWorkout.getImagePath()));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(imgRrc);
    }

    @GetMapping("admin/workouts/{workoutId}") //단건 조회 (TEST 완료)
    public WorkoutResponseDto findWorkout(@PathVariable("workoutId") Long workoutId,
                                          @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        Workout findWorkout = workoutService.findOne(workoutId);
        return new WorkoutResponseDto(findWorkout.getEnglishName(), findWorkout.getKoreanName(), findWorkout.getVideoLink(),
                findWorkout.getDescription(), findWorkout.getBodyParts());
    }

    @GetMapping("admin/workouts/list/{page}") //batch 단위 조회 (TEST 완료)
    public List<WorkoutDto> findWorkouts_page(@PathVariable("page") int page,
                                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        List<Workout> findWorkouts = workoutService.findAll(page);

        return findWorkouts.stream()
                .map(w -> new WorkoutDto(w))
                .collect(Collectors.toList());
    }

    @DeleteMapping("admin/workouts/{workoutId}") //운동 정보 삭제 (TEST 완료)
    public Long deleteWorkout(@PathVariable("workoutId") Long workoutId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        Workout findWorkout = workoutService.findOne(workoutId);
        for (BodyPart bodyPart : findWorkout.getBodyParts()) {
            bodyPart.removeWorkout(findWorkout);
        }

        workoutService.removeWorkout(workoutId);
        return workoutId;
    }

    @Data
    @AllArgsConstructor
    static class WorkoutRequest {
        private String englishName;
        private String koreanName;
        private String videoLink;
        private String description;
        private List<String> bodyPartKoreanName;
        private MultipartFile image;
    }
}