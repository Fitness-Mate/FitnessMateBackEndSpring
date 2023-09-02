package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.MyFitRepository;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyFitService {

    private final MyFitRepository myFitRepository;

    @Transactional
    public void saveMyFit(MyFit myFit) {
        myFitRepository.save(myFit);
    }

    public List<MyWorkout> findAllMyWorkoutWithRoutineId(Long routineId) {
        return myFitRepository.findAllMyWorkoutWithRoutineId(routineId);
    }

    public MyWorkout findMyWorkoutById(Long myWorkoutId) {
        return myFitRepository.findByMyWorkoutById(myWorkoutId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.MY_WORKOUT_NOT_FOUND_EXCEPTION));
    }

    @Transactional
    public void updateMyWorkout(Long myWorkoutId) {
        MyWorkout myWorkout = this.findMyWorkoutById(myWorkoutId);

    }
    @Transactional
    public void deleteMyWorkout(Long myWorkoutId) {
        MyWorkout myWorkout = findMyWorkoutById(myWorkoutId);
        List<MyWorkout> myWorkouts = this.findAllMyWorkoutWithRoutineId(myWorkout.getRoutine().getId());

        int myWorkoutIdx = myWorkouts.indexOf(myWorkout);
        if(myWorkoutIdx < (myWorkouts.size()-1)) {
            for (int i = myWorkoutIdx; i < myWorkouts.size(); i++) {
                myWorkouts.get(i).downMyFitIndex();
            }
        }

        myFitRepository.remove(myWorkout);
    }
}
