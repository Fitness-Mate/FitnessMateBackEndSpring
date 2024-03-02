package FitMate.FitMateBackend.workout.entity;

import FitMate.FitMateBackend.cjjsWorking.dto.workout.WorkoutForm;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "workout_body_part",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "body_part_id"))
    private List<BodyPart> bodyParts = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "workout_machine",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "machine_id"))
    private List<Machine> machines = new ArrayList<>();

    private String englishName;
    private String koreanName;
    private String videoLink;

    @Column(length = 2000)
    private String description;
    private String imgFileName;

    public void update(WorkoutForm form, String imgFileName) {
        this.englishName = form.getEnglishName();
        this.koreanName = form.getKoreanName();
        this.videoLink = form.getVideoLink();
        this.description = form.getDescription();
        this.imgFileName = imgFileName;
    }

    public void removeMachine(Machine machine) {
        this.machines.remove(machine);
    }
}
