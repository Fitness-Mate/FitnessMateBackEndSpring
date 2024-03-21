package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "recommends")
@Getter
@Setter
@DiscriminatorColumn(name = "recommend_type")
public abstract class Recommendation {
    @Id
    @GeneratedValue
    @Column(name = "recommend_id")
    private Long id;

    private String recommendationType; // Workout, Supplement

    @Column(length = 3000)
    private String queryText; // 질문에 들어간 텍스트
    private LocalDate date = LocalDate.now();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="body_data_id")
    private BodyData bodyData;

    public void setUser(User user) {
        this.user = user;
    }


}
