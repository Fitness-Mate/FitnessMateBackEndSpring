package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplementListDto {

    private Long id;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String flavor;
    private String imageURL;
//    private UrlResource image;

    public SupplementListDto(Supplement supplement){
        this.id = supplement.getId();
        this.englishName = supplement.getEnglishName();
        this.koreanName = supplement.getKoreanName();
        this.price = supplement.getPrice();
        this.servings = supplement.getServings();
        this.imageURL = S3FileService.getAccessURL(ServiceConst.S3_DIR_SUPPLEMENT, supplement.getImageName());
//        this.image = new UrlResource("file:" + FileStoreService.getFullPath(supplement.getImagePath()));
        this.flavor = supplement.getFlavor();
    }
}
