package FitMate.FitMateBackend.chanhaleWorking.form.bodyData;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BodyDataForm {
    private LocalDate date = LocalDate.now();
    private Float height = 170.0f;
    private Float weight = 70.0f;
    private Float upperBodyFat = 17.0f;
    private Float lowerBodyFat = 17.0f;
    private Float upperMuscleMass = 40.0f;
    private Float lowerMuscleMass = 40.0f;

//    @Builder
//    public BodyDataForm(Float height, Float weight, Float upperBodyFat, Float lowerBodyFat, Float upperMuscleMass, Float lowerMuscleMass) {
//        this.height = height;
//        this.weight = weight;
//        this.upperBodyFat = upperBodyFat;
//        this.lowerBodyFat = lowerBodyFat;
//        this.upperMuscleMass = upperMuscleMass;
//        this.lowerMuscleMass = lowerMuscleMass;
//    }

    public String validateFields(){
        if (height < 20 || height>350)
            return "적절하지 않은 신장: 20cm 이상, 350cm 이하";
        if (weight < 30 || height>350)
            return "적절하지 않은 몸무게: 30kg 이상, 350kg 이하 ";
        if (upperBodyFat < 0 || upperBodyFat > 95)
            return "적절하지 않은 상체 체지방량 0% 이상, 95%이하";
        if (lowerBodyFat < 0 || lowerBodyFat > 95)
            return "적절하지 않은 하체 체지방량 0% 이상, 95%이하";
        if (upperMuscleMass < 0 || upperMuscleMass > 95)
            return "적절하지 않은 상체 골격근량 0% 이상, 95%이하";
        if (lowerMuscleMass < 0 || lowerMuscleMass > 95)
            return "적절하지 않은 하체 골격근량 0% 이상, 95%이하";
        return "ok";
    }

    public String generateBodyDataStatement(){
        return "my body state is:" +
                "\nheight: " + height +
                "\nweight: " + weight +
                "\nupper body fat: " + upperBodyFat +
                "\nlower body fat: " + lowerBodyFat +
                "\nupper muscle mass: " + upperMuscleMass +
                "\nlower muscle mass: " + lowerMuscleMass +".\n";

    }
}
