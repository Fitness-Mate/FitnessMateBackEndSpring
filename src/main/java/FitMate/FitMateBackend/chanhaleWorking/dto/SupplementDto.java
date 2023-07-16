package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.chanhaleWorking.service.FileStoreService;
import FitMate.FitMateBackend.domain.supplement.BCAA;
import FitMate.FitMateBackend.domain.supplement.Gainer;
import FitMate.FitMateBackend.domain.supplement.Protein;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

/**
 * 보조제 정보 조회 요청에 사용할 dto
 */
@Data
@NoArgsConstructor
public class SupplementDto {

    private Long id;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String description;
    private String marketURL;
    private String supplementType;
//    private UrlResource image;
    private String flavor;
    // protein, gainer
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    private String source;
    // protein, gainer, bcaa
    // bcaa 는 추가요소 없음

    public SupplementDto(Supplement supplement){
        this.id = supplement.getId();
        this.englishName = supplement.getEnglishName();
        this.koreanName = supplement.getKoreanName();
        this.price = supplement.getPrice();
        this.servings = supplement.getServings();
        this.description = supplement.getDescription();
        this.marketURL = supplement.getMarketURL();
        this.flavor = supplement.getFlavor();
//        this.image = new UrlResource("file:" + FileStoreService.getFullPath(supplement.getImagePath()));
        if (supplement instanceof Gainer) {
            this.supplementType = "Gainer";
            Gainer sup = (Gainer) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        } else if (supplement instanceof BCAA) {
            this.supplementType = "BCAA";
            BCAA sup = (BCAA) supplement;
        } else if(supplement instanceof Protein) {
            this.supplementType = "Protein";
            Protein sup = (Protein) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        }

    }

}
