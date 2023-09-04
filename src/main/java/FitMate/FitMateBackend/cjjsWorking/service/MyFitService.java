package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.MyFitRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.RoutineRepository;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyFitService {

    private final MyFitRepository myFitRepository;
    private final RoutineRepository routineRepository;

    @Transactional
    public String saveMyFit(MyFit myFit) {
        myFitRepository.save(myFit);
        return "[myFitId:" + myFit.getId() +"] 등록 완료";
    }

    public List<MyWorkout> findAllMyWorkoutWithRoutineId(Long routineId) {
        routineRepository.findById(routineId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));

        return myFitRepository.findAllMyWorkoutWithRoutineId(routineId);
    }

    public MyWorkout findMyWorkoutById(Long myWorkoutId) {
        return myFitRepository.findByMyWorkoutById(myWorkoutId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_WORKOUT_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public String updateMyWorkout(Long myWorkoutId, MyWorkoutUpdateRequest request) {
        MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);
        myWorkout.update(request);
        return "[myFitId:" + myWorkout.getId() +"] 수정 완료";
    }

    @Transactional
    public String deleteMyWorkout(Long myWorkoutId) {
        MyWorkout myWorkout = findMyWorkoutById(myWorkoutId);
        List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(myWorkout.getRoutine().getId());

        int myWorkoutIdx = myWorkouts.indexOf(myWorkout);
        if(myWorkoutIdx < (myWorkouts.size()-1)) {
            for (int i = myWorkoutIdx; i < myWorkouts.size(); i++) {
                myWorkouts.get(i).downMyFitIndex();
            }
        }

        myFitRepository.remove(myWorkout);
        return "[myFitId:" + myWorkout.getId() +"] 삭제 완료";
    }
}
