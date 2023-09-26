package FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementCreateRequest {
    List<Long> supplementIds = new ArrayList<>();
}
