package FitMate.FitMateBackend.domain;

import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.domain.recommendation.Recommendation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String userName;
    private String loginEmail;
    private String password;
    private String sex;
    private String type; // Admin 여부 표기용 "Admin", "Customer"
    private LocalDate birthDate;    // 생년월일 (추가: 2023.08.02)
    private LocalDate joinDate = LocalDate.now();     // 가입년월일 (추가: 2023.08.02)
//    private Float height; // 체성분으로 이동
//    private Float weight; // 체성분으로 이동
//
//    @Enumerated(EnumType.STRING)
//    private BodyShape bodyShape; // 더이상 보관하지 않음.

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BodyData> bodyDataHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recommendation> recommendationHistory = new ArrayList<>();

    public void addBodyDataHistory(BodyData bodyData){
        bodyDataHistory.add(bodyData);
        bodyData.setUser(this);
    }

    public void addRecommendationHistory(Recommendation recommendation) {
        recommendationHistory.add(recommendation);
        recommendation.setUser(this);
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

    public void updateUser(UpdateUserForm updateUserForm) {
        this.userName = updateUserForm.getUserName();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
    public void setSortBodyData(List<BodyData> sortedBodyData) {
        this.bodyDataHistory = sortedBodyData;
    }
}