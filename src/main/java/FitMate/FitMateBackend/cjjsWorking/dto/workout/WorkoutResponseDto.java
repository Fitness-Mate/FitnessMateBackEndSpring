package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutResponseDto {
    private String englishName;
    private String koreanName;
    private String imgPath;
    private String videoLink;
    private String description;
    private List<String> bodyPartKoreanName = new ArrayList<>();

    public WorkoutResponseDto(String englishName, String koreanName, String fileName, String videoLink, String description, List<BodyPart> bodyParts) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.imgPath = ServiceConst.S3_URL + ServiceConst.S3_DIR_WORKOUT + "/" + fileName;
        this.videoLink = videoLink;
        this.description = description;
        for (BodyPart bodyPart : bodyParts) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}
