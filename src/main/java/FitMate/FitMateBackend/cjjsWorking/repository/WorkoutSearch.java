package FitMate.FitMateBackend.cjjsWorking.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkoutSearch {

    private String searchKeyword;
    private List<String> bodyPartKoreanName;
}