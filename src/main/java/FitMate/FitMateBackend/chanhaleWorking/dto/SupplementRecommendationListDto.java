package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.domain.recommendation.RecommendedSupplement;
import FitMate.FitMateBackend.domain.recommendation.SupplementRecommendation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Getter
@NoArgsConstructor
public class SupplementRecommendationListDto {
    private Long supplementRecommendationId;
    private LocalDate date;
    private String question;
    public static SupplementRecommendationListDto createSupplementRecommendationDto(SupplementRecommendation supplementRecommendation) {
        SupplementRecommendationListDto supplementRecommendationListDto = new SupplementRecommendationListDto();
        supplementRecommendationListDto.supplementRecommendationId = supplementRecommendation.getId();
        supplementRecommendationListDto.date = supplementRecommendation.getDate();
        supplementRecommendationListDto.question = supplementRecommendation.getQueryText();
        return supplementRecommendationListDto;
    }
}
