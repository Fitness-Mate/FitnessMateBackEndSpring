package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Workout;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutDto {
    private Long id;
    private String englishName;
    private String koreanName;
    private String imgPath;
    private String videoLink;
    private String description;
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public WorkoutDto(Workout workout) {
        this.id = workout.getId();
        this.englishName = workout.getEnglishName();
        this.koreanName = workout.getKoreanName();
        this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT + "/" + workout.getImgFileName();
        this.videoLink = workout.getVideoLink();
        this.description = workout.getDescription();
        for (BodyPart bodyPart : workout.getBodyParts()) {
            bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}
