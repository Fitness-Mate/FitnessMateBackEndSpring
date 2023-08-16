package FitMate.FitMateBackend.cjjsWorking.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutForm {
    private String englishName;
    private String koreanName;
    private String videoLink;
    private String description;
    private List<String> bodyPartKoreanName;
    private MultipartFile image;
}
