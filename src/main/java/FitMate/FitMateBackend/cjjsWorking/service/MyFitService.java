package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement.MySupplementUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.myWorkout.MyWorkoutUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.MyFitRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.RoutineRepository;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MySupplement;
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

    public List<MySupplement> findAllMySupplementWithRoutineId(Long routineId) {
        return myFitRepository.findAllMySupplementWithRoutineId(routineId);
    }

    public MyWorkout findMyWorkoutById(Long myWorkoutId) {
        return myFitRepository.findMyWorkoutById(myWorkoutId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_WORKOUT_NOT_FOUND_EXCEPTION));
    }

    public MySupplement findMySupplementById(Long mySupplementId) {
        return myFitRepository.findMySupplementById(mySupplementId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_SUPPLEMENT_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public String updateMyWorkout(Long myWorkoutId, MyWorkoutUpdateRequest request) {
        MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);
        List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(myWorkout.getRoutine().getId());

        int oldIndex = myWorkout.getMyFitIndex();
        int newIndex = request.getMyWorkoutIndex();
        if(oldIndex > newIndex) {
            //만약 4에서 1로 이동했다면.. 원래 1, 2, 3을 2,3,4로 바꿔준다.
            for (int i = 0; i < (oldIndex-1); i++) {
                myWorkouts.get(i).upMyFitIndex();
            }

        } else if(oldIndex < newIndex) {
            //만약 1에서 4로 이동했다면.. 원래 2, 3, 4를 1, 2, 3으로 바꿔준다.
            for (int i = oldIndex; i < newIndex; i++) {
                myWorkouts.get(i).downMyFitIndex();
            }
        }
        myWorkout.update(request);
        return "[myFitId:" + myWorkout.getId() +"] 수정 완료";
    }

    @Transactional
    public String updateMySupplement(Long mySupplementId, MySupplementUpdateRequest request) {
        MySupplement mySupplement = this.findMySupplementById(mySupplementId);
        List<MySupplement> mySupplements = this.findAllMySupplementWithRoutineId(mySupplement.getRoutine().getId());

        int oldIndex = mySupplement.getMyFitIndex();
        int newIndex = request.getMySupplementIndex();
        if(oldIndex > newIndex) {
            //만약 4에서 1로 이동했다면.. 원래 1, 2, 3을 2,3,4로 바꿔준다.
            for (int i = 0; i < (oldIndex-1); i++) {
                mySupplements.get(i).upMyFitIndex();
            }

        } else if(oldIndex < newIndex) {
            //만약 1에서 4로 이동했다면.. 원래 2, 3, 4를 1, 2, 3으로 바꿔준다.
            for (int i = oldIndex; i < newIndex; i++) {
                mySupplements.get(i).downMyFitIndex();
            }
        }

        mySupplement.update(request);
        return "[myFitId:" + mySupplementId +"] 수정 완료";
    }

    @Transactional
    public String deleteMyWorkout(Long myWorkoutId) {
        MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);
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

    @Transactional
    public String deleteMySupplement(Long mySupplementId) {
        MySupplement mySupplement = this.findMySupplementById(mySupplementId);
        List<MySupplement> mySupplements = this.findAllMySupplementWithRoutineId(mySupplement.getRoutine().getId());

        int mySupplementIdx = mySupplements.indexOf(mySupplement);
        if(mySupplementIdx < (mySupplements.size()-1)) {
            for (int i = mySupplementIdx; i < mySupplements.size(); i++) {
                mySupplements.get(i).downMyFitIndex();
            }
        }

        myFitRepository.remove(mySupplement);
        return "[myFitId:" + mySupplement.getId() +"] 삭제 완료";
    }
}
