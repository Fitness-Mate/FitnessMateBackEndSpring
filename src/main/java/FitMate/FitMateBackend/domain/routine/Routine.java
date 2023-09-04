package FitMate.FitMateBackend.domain.routine;

import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.myfit.MyFit;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn //default: DTYPE
public abstract class Routine {

    @Id @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private final List<MyFit> myFit = new ArrayList<>();

    private String routineName;
    private int routineIndex;

    public Routine() {}
    public Routine(User user, String routineName, int routineIndex) {
        this.user = user;
        this.routineName = routineName;
        this.routineIndex = routineIndex;
    }

    public void update(String routineName, int routineIndex) {
        this.routineName = routineName;
        this.routineIndex = routineIndex;
    }
}
