package FitMate.FitMateBackend.chanhaleWorking.form.recommendation;

import FitMate.FitMateBackend.domain.Purpose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SupplementRecommendationForm {
    private Long monthlyBudget;
    private List<Purpose> purpose;
}
