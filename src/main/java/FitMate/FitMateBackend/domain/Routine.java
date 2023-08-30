package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.domain.myfit.MyFit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "routine")
@NoArgsConstructor
public class Routine {

    @Id @GeneratedValue
    @Column(name = "routine_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<MyFit> myFit;

    private String name;
}
