package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.RoutineRepository;
import FitMate.FitMateBackend.domain.Routine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.myfit.MyWorkout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public ResponseEntity<String> saveRoutine(User user, String routineName) {
        Routine routine = new Routine(user, routineName);
        routineRepository.save(routine);
        return ResponseEntity.ok("[" + user.getUserName() + ":" + routineName + "] 루틴 생성 완료");
    }

    public void saveMyWorkout(MyWorkout myWorkout) {

    }

    public Routine findById(Long routineId) {
        return null;
    }
}
