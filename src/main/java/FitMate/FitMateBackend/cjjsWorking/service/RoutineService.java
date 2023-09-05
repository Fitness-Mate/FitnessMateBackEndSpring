package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.RoutineSetData;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.RoutineSetRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.SupplementRoutineUpdateRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.RoutineRepository;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.routine.Routine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.routine.SupplementRoutine;
import FitMate.FitMateBackend.domain.routine.WorkoutRoutine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RoutineService {

    private final RoutineRepository routineRepository;

    @Transactional
    public Long saveWorkoutRoutine(User user, String routineName, int routineIndex) {
        Routine routine = new WorkoutRoutine(user, routineName, routineIndex);
        return routineRepository.save(routine);
    }

    @Transactional
    public Long saveSupplementRoutine(User user) {
        Routine routine = new SupplementRoutine(user, null, 0);
        return routineRepository.save(routine);
    }

    @Transactional
    public void updateRoutine(RoutineSetData routine) {
        Routine findRoutine = findRoutineById(routine.getRoutineId());
        findRoutine.update(routine.getRoutineName(), routine.getRoutineIndex());
    }

    @Transactional
    public void updateSupplementRoutineName(Long userId, SupplementRoutineUpdateRequest request) {
        Routine routine = findSupplementRoutineByUserId(userId);
        routine.update(request.getRoutineName(), 0);
    }

    public Routine findRoutineById(Long routineId) {
        return routineRepository.findById(routineId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));
    }

    public Routine findSupplementRoutineByUserId(Long userId) {
        return routineRepository.findSupplementRoutineByUserId(userId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.ROUTINE_NOT_FOUND_EXCEPTION));
    }

    public List<WorkoutRoutine> findAllWorkoutRoutineWithIndex(Long userId) {
        return routineRepository.findAllWorkoutRoutineWithIndex(userId);
    }
    @Transactional
    public void removeRoutine(Routine routine) {
        routineRepository.remove(routine);
    }

    @Transactional
    public void setWorkoutRoutines(User user, RoutineSetRequest request) {
        if(request.getRoutines().size() >= ServiceConst.ROUTINE_MAX_SIZE)
            throw new CustomException(CustomErrorCode.ROUTINE_SIZE_OVER_EXCEPTION);

        List<Long> routineIds = new ArrayList<>();
        for (RoutineSetData routine : request.getRoutines()) {
            if(routine.getRoutineId() == -1) {
                //create routine
                Long savedRoutineId = this.saveWorkoutRoutine(user, routine.getRoutineName(), routine.getRoutineIndex());
                routineIds.add(savedRoutineId);
            } else {
                //update routine
                routineIds.add(routine.getRoutineId());
                this.updateRoutine(routine);
            }
        }

        //delete routine
        List<WorkoutRoutine> routines = this.findAllWorkoutRoutineWithIndex(user.getId());
        for (WorkoutRoutine routine : routines) {
            if(!routineIds.contains(routine.getId())) {
                this.removeRoutine(routine);
            }
        }
    }
}
