package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.User;
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

    @Column(length = 2000)
    private String queryText; // 질문에 들어간 텍스트
    private LocalDate date = LocalDate.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="body_data_id")
    private BodyData bodyData;

    public void setUser(User user) {
        this.user = user;
    }


}
