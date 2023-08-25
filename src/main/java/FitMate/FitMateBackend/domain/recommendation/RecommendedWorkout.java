package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.Workout;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class RecommendedWorkout {

    @Id @GeneratedValue
    @Column(name = "recommended_workout_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private WorkoutRecommendation workoutRecommendation;

    private String englishName;
    private String koreanName;
    private String imgPath;

    private String weight;
    private String repeat;
    private String set;

    @Column(length = 2000)
    private String workoutDescription;

//    @Column(length = 2000)
//    private String korDescription;
//
//    @Column(length = 2000)
//    private String engDescription;

    public void update(WorkoutRecommendation workoutRecommendation, String englishName, String koreanName, String imgPath,
                       String workoutDescription, String weight, String repeat, String set) {
        this.workoutRecommendation = workoutRecommendation;
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.imgPath = imgPath;
        this.workoutDescription = workoutDescription;

        this.weight = weight;
        this.repeat = repeat;
        this.set = set;
    }
}
