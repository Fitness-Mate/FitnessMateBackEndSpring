package FitMate.FitMateBackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Machine {

    @Id @GeneratedValue
    @Column(name = "machine_id")
    private Long id;

    @JsonIgnore
    @ManyToMany(mappedBy = "machines")
    private List<BodyPart> bodyParts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "machines")
    private List<Workout> workouts = new ArrayList<>();

    private String englishName;
    private String koreanName;

    public void update(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }

    public void removeWorkout(Workout workout) {
        this.workouts.remove(workout);
    }
}
