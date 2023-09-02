package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.domain.myfit.MyFit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "routine")
public class Routine {

    @Id @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private final List<MyFit> myFit = new ArrayList<>();

    private String routineName;

    public Routine(User user, String routineName) {
        this.user = user;
        this.routineName = routineName;
    }
}
