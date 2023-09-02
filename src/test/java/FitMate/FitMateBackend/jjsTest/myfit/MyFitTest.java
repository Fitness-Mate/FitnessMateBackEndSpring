package FitMate.FitMateBackend.jjsTest.myfit;

import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import FitMate.FitMateBackend.cjjsWorking.service.WorkoutService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@SpringBootTest
public class MyFitTest {

    @Autowired private UserService userService;
    @Autowired private WorkoutService workoutService;

    @Test
    public void main() {
//        System.out.println(readUserInfo());
    }

    @Test
    public int readUserInfo() {
        User user = userService.getUserWithId(2L);
        int age = LocalDate.now().getYear() - user.getBirthDate().getYear();

        if((LocalDate.now().getMonthValue() < user.getBirthDate().getMonthValue())) {
            return age-1;
        } else if(LocalDate.now().getMonthValue() > user.getBirthDate().getMonthValue()) {
            return age;
        } else {
            if(LocalDate.now().getDayOfMonth() < user.getBirthDate().getDayOfMonth()) {
                return age-1;
            } else {
                return age;
            }
        }
    }
}
