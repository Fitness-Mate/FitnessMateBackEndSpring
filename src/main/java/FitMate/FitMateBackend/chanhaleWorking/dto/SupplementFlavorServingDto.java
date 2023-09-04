package FitMate.FitMateBackend.chanhaleWorking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplementFlavorServingDto {
    private Long supplementId;
    private Float servings;
    private Integer price;
}
