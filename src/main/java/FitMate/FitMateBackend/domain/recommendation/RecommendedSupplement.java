package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.supplement.Supplement;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "recommended_supplements")
@NoArgsConstructor
public class RecommendedSupplement {
    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    SupplementRecommendation supplementRecommendation;


    @ManyToOne
    private Supplement supplement;
    @Column(length = 2000)
    private String englishRecommendationString;
    @Column(length = 2000)
    private String koreanRecommendationString;

    public static RecommendedSupplement createRecommendedSupplement(Supplement supplement, String englishRecommendationString, String koreanRecommendationString) {
        RecommendedSupplement recommendedSupplement = new RecommendedSupplement();
        recommendedSupplement.supplement = supplement;
        recommendedSupplement.englishRecommendationString = englishRecommendationString;
        recommendedSupplement.koreanRecommendationString = koreanRecommendationString;
        return recommendedSupplement;
    }
}
