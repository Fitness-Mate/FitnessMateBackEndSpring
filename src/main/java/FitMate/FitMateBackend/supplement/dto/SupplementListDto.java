package FitMate.FitMateBackend.supplement.dto;

import FitMate.FitMateBackend.common.util.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.supplement.entity.Gainer;
import FitMate.FitMateBackend.supplement.entity.Protein;
import FitMate.FitMateBackend.supplement.entity.Supplement;
import FitMate.FitMateBackend.supplement.entity.SupplementType;
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
    private String supplementType;
    private String flavor;
    private String imageURL;
    private String source;
    private String description;
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
        this.description = supplement.getDescription();
        if (supplement.getType() == SupplementType.Gainer) {
            this.supplementType = "Gainer";
            this.source = ((Gainer) supplement).getSource();
        }
        if (supplement.getType() == SupplementType.AminoAcid) {
            this.supplementType = "AminoAcid";
        }
        if (supplement.getType() == SupplementType.Protein) {
            this.supplementType = "Protein";
            this.source = ((Protein) supplement).getSource();
        }
        if (supplement.getType() == SupplementType.Other) {
            this.supplementType = "Other";
        }
    }
}
