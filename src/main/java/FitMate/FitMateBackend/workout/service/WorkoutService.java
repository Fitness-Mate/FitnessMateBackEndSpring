package FitMate.FitMateBackend.workout.service;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutSearchCond;
import FitMate.FitMateBackend.workout.dto.WorkoutResponseDto;
import java.util.List;

public interface WorkoutService {

    /**
     * Workout 단건 조회 서비스
     * @param id 조회할 Workout의 id(PK)
     * @return 조회된 Workout 반환
     * */
    WorkoutResponseDto findById(Long id);

    /**
     * Workout 검색 서비스
     * @param page 검색 대상 페이지 번호
     * @param cond 검색 키워드 및 옵션 객체
     * @return 검색 조건에 부합하는 Workout 리스트 반환
     * */
    List<WorkoutResponseDto> searchAll(int page, WorkoutSearchCond cond);
}