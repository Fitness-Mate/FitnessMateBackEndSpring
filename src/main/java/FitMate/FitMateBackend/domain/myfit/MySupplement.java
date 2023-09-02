package FitMate.FitMateBackend.domain.myfit;

import FitMate.FitMateBackend.domain.Routine;
import jakarta.persistence.Entity;

@Entity
public class MySupplement extends MyFit {

    public MySupplement() {
        super();
    }

    public MySupplement(Routine routine) {
        super(routine);
    }
}