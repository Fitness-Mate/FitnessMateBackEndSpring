package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Workout {

    @Id @GeneratedValue
    @Column(name = "workout_id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "workout_body_part",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "body_part_id"))
    private List<BodyPart> bodyParts = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    private String englishName;
    private String koreanName;
    private String videoLink;
    private String description;
    private String imgFileName;

    public void update(WorkoutForm form, Machine machine, String imgFileName) {
        this.englishName = form.getEnglishName();
        this.koreanName = form.getKoreanName();
        this.videoLink = form.getVideoLink();
        this.description = form.getDescription();
        this.imgFileName = imgFileName;
        this.machine = machine;
    }
}
