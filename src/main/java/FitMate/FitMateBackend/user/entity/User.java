package FitMate.FitMateBackend.user.entity;

import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.recommendation.Recommendation;
import FitMate.FitMateBackend.domain.routine.Routine;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String loginEmail;
    private String password;
    private String sex;
    private String type; // Admin 여부 표기용 "Admin", "Customer"
    private LocalDate birthDate;    // 생년월일 (추가: 2023.08.02)
    private LocalDate joinDate = LocalDate.now();     // 가입년월일 (추가: 2023.08.02)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BodyData> bodyDataHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recommendation> recommendationHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Routine> routines = new ArrayList<>();

    public void addBodyDataHistory(BodyData bodyData){
        bodyDataHistory.add(bodyData);
        bodyData.setUser(this);
    }

    public void addRecommendationHistory(Recommendation recommendation) {
        recommendationHistory.add(recommendation);
        recommendation.setUser(this);
    }
    public void addRoutine(Routine routine){
        routines.add(routine);
    }
    public static User createUser(RegisterForm form, String type) {
        User user = new User();
        user.userName = form.getUserName();
        user.loginEmail = form.getLoginEmail();
        user.password = form.getPassword();
        user.sex = form.getSex();
        user.type = type;
        user.birthDate = form.getBirthDate();
        return user;
    }
    public static User createUserTest(RegisterForm form, String password, String type) {
        User user = new User();
        user.userName = form.getUserName();
        user.loginEmail = form.getLoginEmail();
        user.password = password;
        user.sex = form.getSex();
        user.type = type;
        user.birthDate = form.getBirthDate();
        return user;
    }

    public void updateUser(UpdateUserForm updateUserForm) {
        this.userName = updateUserForm.getUserName();
        this.birthDate = updateUserForm.getBirthDate();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
    public void setSortBodyData(List<BodyData> sortedBodyData) {
        this.bodyDataHistory = sortedBodyData;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getAge() {
        int age = LocalDate.now().getYear() - this.birthDate.getYear();

        if((LocalDate.now().getMonthValue() < this.birthDate.getMonthValue())) {
            return age-1;
        } else if(LocalDate.now().getMonthValue() > this.birthDate.getMonthValue()) {
            return age;
        } else {
            if(LocalDate.now().getDayOfMonth() < this.birthDate.getDayOfMonth()) {
                return age-1;
            } else {
                return age;
            }
        }
    }
}