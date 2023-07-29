package FitMate.FitMateBackend.workoutTest;

import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.WorkoutSearch;
import FitMate.FitMateBackend.domain.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class WorkoutSearchTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    public void searchTest() {
        //given
        String searchKeyword = "풀 다운 랫,///..,ㅁㄴㅇㄹㅇ 데드,, 리프트";
        List<String> bodyPartKoreanName = new ArrayList<>();
        bodyPartKoreanName.add("등");

        //when
        List<Workout> workouts = workoutRepository.searchAll(1, new WorkoutSearch(searchKeyword, bodyPartKoreanName));

        //then
        for (Workout workout : workouts) {
            System.out.println(workout.getKoreanName());
        }
    }
}
