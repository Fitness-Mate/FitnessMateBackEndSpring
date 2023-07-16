package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.domain.recommendation.RecommendedSupplement;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class RecommendedSupplementDto {

    private Long id;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String flavor;
    private String description;
    private String koreanRecommendation;

    public static RecommendedSupplementDto createRecommendedSupplementDto(RecommendedSupplement rc) {
        Supplement supplement = rc.getSupplement();
        RecommendedSupplementDto recommendedSupplementDto = new RecommendedSupplementDto();
        recommendedSupplementDto.id = supplement.getId();
        recommendedSupplementDto.englishName = supplement.getEnglishName();
        recommendedSupplementDto.koreanName = supplement.getKoreanName();
        recommendedSupplementDto.price = supplement.getPrice();
        recommendedSupplementDto.servings = supplement.getServings();
        recommendedSupplementDto.flavor = supplement.getFlavor();
        recommendedSupplementDto.description = supplement.getDescription();
        recommendedSupplementDto.koreanRecommendation = rc.getKoreanRecommendationString();
        return recommendedSupplementDto;
    }
}
