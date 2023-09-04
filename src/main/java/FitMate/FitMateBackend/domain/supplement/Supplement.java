package FitMate.FitMateBackend.domain.supplement;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplements")
@Getter
@DiscriminatorColumn(name = "supplement_type")
@NoArgsConstructor
public abstract class Supplement {
    @Id
    @GeneratedValue
    @Column(name = "supplement_id")
    private Long id;

    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    @Column(length = 2000)
    private String description;
    private String marketURL;
    private String flavor;
    private SupplementType type;
    private Boolean isCaptain;  // 제품군의 대표인지 (제품군의 주장일 경우 ChatGPT의 질의문에 포함된다.)
                                // 무맛이 주장이 된다.

    // 앞에 디렉터리 정보를 제외한 상대경로를 저장
    private String imageName;

    public Supplement(SupplementForm supplementForm) {
        this.englishName = supplementForm.getEnglishName();
        this.koreanName = supplementForm.getKoreanName();
        this.price = supplementForm.getPrice();
        this.servings = supplementForm.getServings();
        this.description = supplementForm.getDescription();
        this.marketURL = supplementForm.getMarketURL();
        this.flavor = supplementForm.getFlavor();
        this.type = supplementForm.getSupplementType();
        this.isCaptain = supplementForm.getIsCaptain();
    }

    public void updateFields(SupplementForm supplementForm) {
        this.englishName = supplementForm.getEnglishName();
        this.koreanName = supplementForm.getKoreanName();
        this.price = supplementForm.getPrice();
        this.servings = supplementForm.getServings();
        this.description = supplementForm.getDescription();
        this.marketURL = supplementForm.getMarketURL();
        this.flavor = supplementForm.getFlavor();
        this.type = supplementForm.getSupplementType();
        this.isCaptain = supplementForm.getIsCaptain();
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public abstract String createIntroduction();
}
