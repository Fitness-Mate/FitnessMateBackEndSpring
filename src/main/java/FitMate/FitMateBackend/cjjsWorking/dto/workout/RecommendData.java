package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecommendData {
    private String koreanName;
    private String englishName;
    private String machineName;
    private List<String> bodyPartKoreanName = new ArrayList<>();
    private String description;
    private String imgPath;
    private String weight;
    private String repeat;
    private String set;

    public RecommendData(RecommendedWorkout recommend) {
        this.koreanName = recommend.getWorkout().getKoreanName();
        this.englishName = recommend.getWorkout().getEnglishName();
        this.machineName = recommend.getWorkout().getMachine().getKoreanName();
        this.description = recommend.getWorkout().getDescription();
        this.imgPath = S3FileService.getAccessURL(ServiceConst.S3_DIR_WORKOUT, recommend.getWorkout().getImgFileName());
        this.weight = recommend.getWeight();
        this.repeat = recommend.getRepeats();
        this.set = recommend.getSets();

        for (BodyPart bodyPart : recommend.getWorkout().getBodyParts()) {
            this.bodyPartKoreanName.add(bodyPart.getKoreanName());
        }
    }
}
