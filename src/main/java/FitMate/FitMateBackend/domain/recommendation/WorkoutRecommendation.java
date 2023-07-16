package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("Workout")
public class WorkoutRecommendation extends Recommendation {

    @JsonIgnore
    @OneToMany(mappedBy = "workoutRecommendation")
    private List<RecommendedWorkout> rws = new ArrayList<>();

    public static WorkoutRecommendation createWorkoutRecommendation
            (BodyData bodyData, User user, List<BodyPart> bodyParts, List<Machine> machines) {
        WorkoutRecommendation workoutRecommendation = new WorkoutRecommendation();
        workoutRecommendation.setBodyData(bodyData);
        workoutRecommendation.setUser(user);

        workoutRecommendation.setRecommendationType("Workout");

        String bodyPartQuery = updateBodyPartQuery(bodyParts);
        String machineQuery = updateMachineQuery(machines);

        String qString = "suggest up to 3 workouts with id and description in this list and id should only be wrapped in <<<>>>. "
            + "also, you have to explain including weight and number of sets and considering sex and body data described later."
            + "present each workout as a single line.\nFor a ";
        qString = qString.concat(user.getSex().equals("남성") ? "man" : "woman").concat(" who is ");
        qString = qString.concat(bodyData.describe());

        qString = qString.concat(user.getSex().equals("남성") ? " He" : " She").concat(" wants to work out").
                concat(user.getSex().equals("남성") ? " His " : " Her ").concat(bodyPartQuery);
        if(machineQuery != null) qString = qString.concat(" using ").concat(machineQuery);
        qString = qString.concat(".");

        workoutRecommendation.setQueryText(qString);
        return workoutRecommendation;
    }

    public static String updateBodyPartQuery(List<BodyPart> bodyParts) {
        String bodyPartQuery = bodyParts.get(0).getEnglishName();
        for (int i = 1; i < bodyParts.size(); i++) {
            if (i == (bodyParts.size()-1)) bodyPartQuery = bodyPartQuery.concat(" and ").concat(bodyParts.get(i).getEnglishName());
            else bodyPartQuery = bodyPartQuery.concat(", ").concat(bodyParts.get(i).getEnglishName());
        }
        return bodyPartQuery;
    }

    public static String updateMachineQuery(List<Machine> machines) {
        if(machines == null) return null;
        String machineQuery = machines.get(0).getEnglishName();
        for (int i = 1; i < machines.size(); i++) {
            if (i == (machines.size()-1)) machineQuery = machineQuery.concat(" and ").concat(machines.get(i).getEnglishName());
            else machineQuery = machineQuery.concat(", ").concat(machines.get(i).getEnglishName());
        }
        return machineQuery;
    }
}
