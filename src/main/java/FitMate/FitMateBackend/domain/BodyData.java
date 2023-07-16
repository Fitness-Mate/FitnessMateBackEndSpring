package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "body_data")
@Getter
@NoArgsConstructor
public class BodyData {
    @Id
    @GeneratedValue
    @Column(name = "body_data_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDate date;
    private Float height;
    private Float weight;
    private Float upperBodyFat;
    private Float lowerBodyFat;
    private Float upperMuscleMass;
    private Float lowerMuscleMass;

    public static BodyData createBodyData(BodyDataForm bodyDataForm) {
        BodyData bodyData = new BodyData();
        bodyData.date = bodyDataForm.getDate();
        bodyData.height = bodyDataForm.getHeight();
        bodyData.weight = bodyDataForm.getWeight();
        bodyData.upperBodyFat = bodyDataForm.getUpperBodyFat();
        bodyData.lowerBodyFat = bodyDataForm.getLowerBodyFat();
        bodyData.upperMuscleMass = bodyDataForm.getUpperMuscleMass();
        bodyData.lowerMuscleMass = bodyDataForm.getLowerMuscleMass();
        return bodyData;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String describe() {
        String str = "";
        return str.concat(height.toString()).concat("cm tall, ")
                .concat("weights ").concat(weight.toString()).concat("kg, ")
                .concat("has ").concat(upperBodyFat.toString()).concat("% upper body fat, ")
                .concat(lowerBodyFat.toString()).concat("% lower body fat, ")
                .concat(upperMuscleMass.toString()).concat("% upper body skeletal muscle mass, and ")
                .concat(lowerMuscleMass.toString()).concat("% lower body skeletal muscle mass.");
    }

}
