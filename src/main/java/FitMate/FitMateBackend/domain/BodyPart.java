package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.workout.entity.Workout;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="body_part")
@Getter
@NoArgsConstructor
public class BodyPart {

    @Id @GeneratedValue
    @Column(name = "body_part_id")
    private Long id;

    @ManyToMany(mappedBy = "bodyParts")
    private List<Workout> workouts = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "body_part_machine",
        joinColumns = @JoinColumn(name = "body_part_id"),
        inverseJoinColumns = @JoinColumn(name ="machine_id"))
    private List<Machine> machines = new ArrayList<>();

    private String englishName;
    private String koreanName;

    public void update(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }

    public void addMachine(Machine machine) {
        machines.add(machine);
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
    }

    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    public void removeWorkout(Workout workout) {
        workouts.remove(workout);
    }
}