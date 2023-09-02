package FitMate.FitMateBackend.domain.myfit;

import FitMate.FitMateBackend.domain.Routine;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MySupplement extends MyFit {

    public MySupplement(Routine routine, int myFitIndex) {
        super(routine, myFitIndex);
    }
}