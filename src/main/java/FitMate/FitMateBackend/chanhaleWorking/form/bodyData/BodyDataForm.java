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
    private Float bodyFat = 17.0f;
    private Float muscleMass = 40.0f;
    private Float upDownBalance = 0.5f; // 0.5는 균형, 0으로 갈 수록 하체 발달, 1으로 갈 수록 상체발달

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
        if (bodyFat < 0 || bodyFat > 95)
            return "적절하지 않은 체지방량 0% 이상, 95%이하";
        if (muscleMass < 0 || muscleMass > 95)
            return "적절하지 않은 골격근량 0% 이상, 95%이하";
        if (upDownBalance < 0 || upDownBalance > 1.0)
            return "적절하지 않은 상하체 밸런스 0 이상, 1이하";
        return "ok";
    }

    public String generateBodyDataStatement(){

        return "my body state is:" +
                "\nheight: " + height +
                "\nweight: " + weight +
                "\nbody fat: " + bodyFat +
                "\nmuscle mass: " + muscleMass +".\n";

    }
}
