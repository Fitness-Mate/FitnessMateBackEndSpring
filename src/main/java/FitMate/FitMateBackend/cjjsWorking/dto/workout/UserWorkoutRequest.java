package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserWorkoutRequest {
    private String searchKeyword;
    private List<String> bodyPartKoreanName;

    public UserWorkoutRequest(String searchKeyword, List<String> bodyPartKoreanName) {
        this.searchKeyword = searchKeyword;
        this.bodyPartKoreanName = bodyPartKoreanName;
    }
}
