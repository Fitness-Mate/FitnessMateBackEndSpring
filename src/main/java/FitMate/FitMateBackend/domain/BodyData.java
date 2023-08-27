package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import FitMate.FitMateBackend.consts.ServiceConst;
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
    private Float bodyFat;
    private Float muscleMass;
    private Float upDownBalance;

    public static BodyData createBodyData(BodyDataForm bodyDataForm) {
        BodyData bodyData = new BodyData();
        bodyData.date = bodyDataForm.getDate();
        bodyData.height = bodyDataForm.getHeight();
        bodyData.weight = bodyDataForm.getWeight();
        bodyData.bodyFat = bodyDataForm.getBodyFat();
        bodyData.muscleMass = bodyDataForm.getMuscleMass();
        bodyData.upDownBalance = bodyDataForm.getUpDownBalance();
        return bodyData;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String describe() {
        String str = "";
        StringBuilder sb = new StringBuilder(str);
        String sex;
        if (user.getSex().equals(ServiceConst.SEX_MALE)) {
            sex = "His";
        } else {
            sex = "Her";
        }
        sb.append(height.toString()).append("cm tall, ")
                .append("weights ").append(weight).append("kg, ")
                .append("has ").append(bodyFat).append("% body fat, ")
                .append(muscleMass).append("% muscle mass. ");
        sb.append(sex);
        if (upDownBalance < 0.3f) {
            sb.append(" lower body is very well developed but ").append(sex).append(" upper body is not.");
        }
        else if (0.3f <= upDownBalance && upDownBalance <0.45f ) {
            sb.append(" lower body is slightly more developed than ").append(sex).append(" upper body.");
        }
        else if (0.45f <= upDownBalance && upDownBalance <=0.55f ) {
            sb.append(" lower body and ").append(sex).append(" upper body is equally developed");
        }
        else if (0.55f < upDownBalance && upDownBalance <=0.7f ) {
            sb.append(" upper body is slightly more developed than ").append(sex).append(" lower body.");
        }else if (0.7f<upDownBalance ) {
            sb.append(" upper body is very well developed but ").append(sex).append(" lower body is not.");
        }
        return sb.toString();
    }

}
