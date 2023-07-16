package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.domain.recommendation.RecommendedSupplement;
import FitMate.FitMateBackend.domain.recommendation.SupplementRecommendation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
public class SupplementRecommendationDto {
    private Long supplementRecommendationId;
    private LocalDate date;
    private String question;
    private Long monthlyBudget;
    private List<RecommendedSupplementDto> recommendedSupplementList;

    public static SupplementRecommendationDto createSupplementRecommendationDto(SupplementRecommendation supplementRecommendation) {
        SupplementRecommendationDto supplementRecommendationDto = new SupplementRecommendationDto();
        supplementRecommendationDto.supplementRecommendationId = supplementRecommendation.getId();
        supplementRecommendationDto.date = supplementRecommendation.getDate();
        supplementRecommendationDto.question = supplementRecommendation.getQueryText();
        supplementRecommendationDto.monthlyBudget = supplementRecommendation.getMonthlyBudget();
        supplementRecommendationDto.recommendedSupplementList = new ArrayList<>();
        for (RecommendedSupplement recommendedSupplement : supplementRecommendation.getRecommendedSupplements()) {
            supplementRecommendationDto.recommendedSupplementList.add(RecommendedSupplementDto.createRecommendedSupplementDto(recommendedSupplement));
        }
        return supplementRecommendationDto;
    }
}
