package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.MyFitSearchRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement.MySupplementReadAllResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement.MySupplementSearchResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement.MySupplementUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutCreateRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutReadAllResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutSearchResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.service.MyFitService;
import FitMate.FitMateBackend.cjjsWorking.service.RoutineService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.myfit.MySupplement;
import FitMate.FitMateBackend.domain.routine.Routine;
import FitMate.FitMateBackend.domain.Workout;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myfit")
public class MyFitController {

    private final MyFitService myFitService;
    private final WorkoutService workoutService;
    private final RoutineService routineService;
    private final SupplementService supplementService;

    @PostMapping("/routines/workout/{routineId}") //루틴에 운동 추가 - 테스트 완료
    public ResponseEntity<String> createMyWorkout(@RequestBody MyWorkoutCreateRequest request,
                                                  @PathVariable("routineId") Long routineId) {
        List<MyWorkout> myWorkouts = myFitService.findAllMyWorkoutWithRoutineId(routineId);
        for (MyWorkout myWorkout : myWorkouts) {
            if(myWorkout.getWorkout().getId().equals(request.getWorkoutId()))
                throw new CustomException(CustomErrorCode.ALREADY_EXIST_MY_WORKOUT_EXCEPTION);
        }
        if(myWorkouts.size() >= ServiceConst.MY_WORKOUT_MAX_SIZE)
            throw new CustomException(CustomErrorCode.MY_WORKOUT_SIZE_OVER_EXCEPTION);

        Workout workout = workoutService.findOne(request.getWorkoutId());
        Routine routine = routineService.findRoutineById(routineId);

        MyFit myWorkout = new MyWorkout(routine, workout, request, myWorkouts.size()+1);
        return ResponseEntity.ok(myFitService.saveMyFit(myWorkout));
    }

    @GetMapping("/routines/workout/{routineId}") //루틴에 속한 운동 리스트 조회 - 테스트 완료
    public List<MyWorkoutReadAllResponse> readMyWorkoutInRoutine(@PathVariable("routineId") Long routineId) {
        return myFitService.findAllMyWorkoutWithRoutineId(routineId).stream()
                .map(MyWorkoutReadAllResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/routines/workout/search/{routineId}") //루틴에 추가할 운동 검색 - 테스트 완료
    public ResponseEntity<List<MyWorkoutSearchResponse>> searchWorkoutWithRoutineId(@PathVariable("routineId") Long routineId,
                                                                                    @RequestBody MyFitSearchRequest request) {
        return ResponseEntity.ok(
                myFitService.searchWorkoutWithRoutineId(request.getSearchKeyword(), routineId).stream()
                        .map(MyWorkoutSearchResponse::new)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/routines/workout/{myFitId}") //루틴에 속한 운동 수정 - 테스트 완료
    public ResponseEntity<String> updateMyWorkout(@PathVariable("myFitId") Long myWorkoutId,
                                                  @RequestBody MyWorkoutUpdateRequest request) {
        return ResponseEntity.ok(myFitService.updateMyWorkout(myWorkoutId, request));
    }

    @DeleteMapping("/routines/workout/{myFitId}") //루틴에 속한 운동 삭제 - 테스트 완료
    public ResponseEntity<String> deleteMyWorkout(@PathVariable("myFitId") Long myWorkoutId) {
        return ResponseEntity.ok(myFitService.deleteMyWorkout(myWorkoutId));
    }

    @PostMapping("/routines/supplement/{supplementId}") //루틴에 보조제 추가 - 테스트 완료
    public ResponseEntity<String> createMySupplement(@PathVariable("supplementId") Long supplementId,
                                                     @RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        Routine routine = routineService.findSupplementRoutineByUserId(userId);
        Supplement supplement = supplementService.findSupplementById(supplementId);

        List<MySupplement> mySupplements = myFitService.findAllMySupplementWithRoutineId(routine.getId());

        //test를 위해 예외처리 잠시 off
//        for (MySupplement mySupplement : mySupplements) {
//            if(mySupplement.getSupplement().getId().equals(supplementId))
//                throw new CustomException(CustomErrorCode.ALREADY_EXIST_MY_SUPPLEMENT_EXCEPTION);
//        }
//        if(mySupplements.size() >= ServiceConst.MY_SUPPLEMENT_MAX_SIZE)
//            throw new CustomException(CustomErrorCode.MY_SUPPLEMENT_SIZE_OVER_EXCEPTION);

        MyFit mySupplement = new MySupplement(routine, supplement, mySupplements.size()+1);
        return ResponseEntity.ok(myFitService.saveMyFit(mySupplement));
    }

    @GetMapping("/routines/supplement") //루틴에 속한 보조제 리스트 조회 - 테스트 완료
    public ResponseEntity<MySupplementReadAllResponse> readMySupplementInRoutine(@RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        Routine routine = routineService.findSupplementRoutineByUserId(userId);

        List<MySupplement> mySupplements = myFitService.findAllMySupplementWithRoutineId(routine.getId());
        return ResponseEntity.ok(new MySupplementReadAllResponse(routine, mySupplements));
    }

    @PostMapping("/routines/supplement/search") //루틴에 추가할 보조제 검색 - 테스트 완료
    public ResponseEntity<List<MySupplementSearchResponse>> searchSupplementWithRoutine(@RequestBody MyFitSearchRequest request,
                                                                                        @RequestHeader HttpHeaders header) {
        Long userId = JwtService.getUserId(JwtService.getToken(header));
        Routine routine = routineService.findSupplementRoutineByUserId(userId);

        return ResponseEntity.ok(myFitService.searchSupplementWithRoutineId(request.getSearchKeyword(), routine.getId()).stream()
                .map(MySupplementSearchResponse::new)
                .collect(Collectors.toList()));
    }

    @PutMapping("/routines/supplement/{myFitId}") //루틴에 속한 보조제 수정 - 테스트 완료
    public ResponseEntity<String> updateMySupplement(@PathVariable("myFitId") Long mySupplementId,
                                                     @RequestBody MySupplementUpdateRequest request) {
        return ResponseEntity.ok(myFitService.updateMySupplement(mySupplementId, request));
    }

    @DeleteMapping("/routines/supplement/{myFitId}") //루틴에 속한 보조제 삭제 - 테스트 완료
    public ResponseEntity<String> deleteMySupplement(@PathVariable("myFitId") Long mySupplementId) {
        return ResponseEntity.ok(myFitService.deleteMySupplement(mySupplementId));
    }
}
