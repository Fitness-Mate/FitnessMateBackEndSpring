package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.chanhaleWorking.service.FileStoreService;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

@Data
@NoArgsConstructor
public class SupplementListDto {

    private Long id;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String flavor;
//    private UrlResource image;

    public SupplementListDto(Supplement supplement){
        this.id = supplement.getId();
        this.englishName = supplement.getEnglishName();
        this.koreanName = supplement.getKoreanName();
        this.price = supplement.getPrice();
        this.servings = supplement.getServings();
//        this.image = new UrlResource("file:" + FileStoreService.getFullPath(supplement.getImagePath()));
        this.flavor = supplement.getFlavor();
    }
}
