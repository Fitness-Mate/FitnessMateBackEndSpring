package FitMate.FitMateBackend.domain.myfit;

import FitMate.FitMateBackend.domain.Routine;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //default: DTYPE
public abstract class MyFit {

    @Id @GeneratedValue
    @Column(name = "my_fit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    public MyFit() {}
    public MyFit(Routine routine) {
        this.routine = routine;
    }
}