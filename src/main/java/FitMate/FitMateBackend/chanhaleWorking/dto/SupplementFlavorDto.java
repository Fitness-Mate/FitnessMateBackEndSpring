package FitMate.FitMateBackend.chanhaleWorking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplementFlavorDto {
    private String flavorName;
    private List<SupplementFlavorServingDto> units;

    public SupplementFlavorDto(String flavorName) {
        this.flavorName = flavorName;
        this.units = new ArrayList<>();
    }
}
