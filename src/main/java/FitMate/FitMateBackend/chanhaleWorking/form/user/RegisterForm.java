package FitMate.FitMateBackend.chanhaleWorking.form.user;

import FitMate.FitMateBackend.chanhaleWorking.form.bodyData.BodyDataForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    private String userName;
    private String loginEmail;
    private String password;
    private String sex;
    private LocalDate date = LocalDate.now();
    private LocalDate birthDate;
    private Float height = 170.0f;
    private Float weight = 70.0f;
    private Float bodyFat = 17.0f;
    private Float muscleMass = 40.0f;
    private Float upDownBalance = 0.5f;
    private String uuid;

    public String validateFields(){
        String regexPattern = "^(.+)@(\\S+)$";
        if (userName.length() > 10)
            return "너무 긴 유저명: 3자리 이상, 10자리 이하";
        if (userName.length() < 3)
            return "너무 짧은 유저명: 3자리 이상, 10자리 이하";
        if (!loginEmail.matches(regexPattern))
            return "형식에 맞지 않는 이메일 주소";
        if (password.length() < 8)
            return "너무 짧은 password: 8자리 이상 필요";
        if (!sex.equals("남성") && !sex.equals("여성"))
            return "성별 선택 오류: 남성 또는 여성";
        if (height < 80 || height>350)
            return "적절하지 않은 신장: 80cm 이상, 350cm 이하";
        if (weight < 30 || weight>350)
            return "적절하지 않은 몸무게: 30kg 이상, 350kg 이하 ";
        if (bodyFat < 0 || bodyFat > 95)
            return "적절하지 않은 체지방량 0% 이상, 95%이하";
        if (muscleMass < 0 || muscleMass > 95)
            return "적절하지 않은 골격근량 0% 이상, 95%이하";
        if (upDownBalance < 0f || upDownBalance > 1f)
            return "적절하지 않은 체형 밸런스 0.0 이상, 1.0이하";
        return "ok";
    }
    public BodyDataForm getBodyDataForm(){
        return new BodyDataForm(date, height, weight, bodyFat, muscleMass, upDownBalance);
    }
}
