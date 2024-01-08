package FitMate.FitMateBackend.recommend.dto;

import FitMate.FitMateBackend.recommend.entity.SupplementRecommendation;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
