package FitMate.FitMateBackend.user.entity;

import FitMate.FitMateBackend.user.dto.RegisterForm;
import FitMate.FitMateBackend.user.dto.UpdateUserForm;
import FitMate.FitMateBackend.recommend.entity.Recommendation;
import FitMate.FitMateBackend.myfit.entity.Routine;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User implements UserDetails {
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

    //🔽🔽🔽 For Spring Security 🔽🔽🔽
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(type));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}