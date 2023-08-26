package FitMate.FitMateBackend.cjjsWorking.dto.workout;

import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.recommendation.RecommendedWorkout;
import lombok.Data;

@Data
public class RecommendData {
    private String koreanName;
    private String englishName;
    // + 운동 기구
    // + 운동 부위
    private String description;
    private String imgPath;
    private String weight;
    private String repeat;
    private String set;

    public RecommendData(RecommendedWorkout recommend) {
        this.koreanName = recommend.getWorkout().getKoreanName();
        this.englishName = recommend.getWorkout().getEnglishName();
        // + 운동 기구
        // + 운동 부위
        this.description = recommend.getWorkout().getDescription();
        this.imgPath = S3FileService.getAccessURL(ServiceConst.S3_DIR_WORKOUT, recommend.getWorkout().getImgFileName());
        this.weight = recommend.getWeight();
        this.repeat = recommend.getRepeats();
        this.set = recommend.getSets();
    }
}
