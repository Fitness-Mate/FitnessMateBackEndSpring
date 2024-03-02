package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.workout.entity.Workout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private String weight;
    private String repeats;
    private String sets;

    public void update(WorkoutRecommendation workoutRecommendation,
                       Workout workout, String weight,
                       String repeats, String sets) {
        this.workoutRecommendation = workoutRecommendation;
        this.workout = workout;
        this.weight = weight;
        this.repeats = repeats;
        this.sets = sets;
    }
}
