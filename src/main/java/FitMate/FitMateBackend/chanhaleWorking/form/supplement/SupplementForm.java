package FitMate.FitMateBackend.chanhaleWorking.form.supplement;

import FitMate.FitMateBackend.domain.supplement.SupplementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplementForm {
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String description;
    private String marketURL;
    private SupplementType supplementType;
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    private String source;
    private String flavor;
    private MultipartFile image;

    public String validateFields() {
        if (price < 0) {
            return "잘못된 가격";
        }
        if (servings < 0) {
            return "잘못된 serving 정보";
        }
//        if (proteinPerServing < 0) {
//            return "잘못된 protein per serving 정보";
//        }
//        if (fatPerServing < 0) {
//            return "잘못된 fat per serving 정보";
//        }
//        if (carbohydratePerServing < 0) {
//            return "잘못된 carbohydrate per serving 정보";
//        }
        return "ok";
    }
}
