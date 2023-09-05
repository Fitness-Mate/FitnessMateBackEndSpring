package FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement;

import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.myfit.MySupplement;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementReadData {
    private Long mySupplementId;
    private int mySupplementIndex;

    private Long supplementId;
    private String supplementName;
    private String imageURL;
    private String description;
    private String supplementType;
    private float servings;
    //    private float proteinPerServing;
//    private float carbohydratePerServing;
    private String marketURL;
    private String flavor;

    public MySupplementReadData(MySupplement mySupplement) {
        this.mySupplementId = mySupplement.getId();
        this.mySupplementIndex = mySupplement.getMyFitIndex();

        Supplement supplement = mySupplement.getSupplement();
        this.supplementId = supplement.getId();
        this.supplementName = supplement.getKoreanName();
        this.imageURL = S3FileService.getAccessURL(ServiceConst.S3_DIR_SUPPLEMENT, supplement.getImageName());
        this.description = supplement.getDescription();
        this.supplementType = supplement.getType().toString();
        this.servings = supplement.getServings();
        this.marketURL = supplement.getMarketURL();
        this.flavor = supplement.getFlavor();
    }

}
