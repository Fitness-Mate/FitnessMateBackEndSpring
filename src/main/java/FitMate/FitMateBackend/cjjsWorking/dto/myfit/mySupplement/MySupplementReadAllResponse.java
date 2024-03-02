package FitMate.FitMateBackend.cjjsWorking.dto.myfit.mySupplement;

import FitMate.FitMateBackend.domain.myfit.MySupplement;
import FitMate.FitMateBackend.domain.routine.Routine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySupplementReadAllResponse {
    private String routineName;
    private List<MySupplementReadData> supplements = new ArrayList<>();

    public MySupplementReadAllResponse(Routine routine, List<MySupplement> mySupplements) {
        this.routineName = routine.getRoutineName();
        for (MySupplement mySupplement : mySupplements) {
            this.supplements.add(new MySupplementReadData(mySupplement));
        }
    }
}
