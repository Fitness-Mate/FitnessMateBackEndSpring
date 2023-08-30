package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.RoutineRepository;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final RoutineRepository routineRepository;

    public void saveRoutine() {

    }

    public void saveMyWorkout(MyWorkout myWorkout) {

    }

    public Routine findById(Long routineId) {
        return null;
    }
}
