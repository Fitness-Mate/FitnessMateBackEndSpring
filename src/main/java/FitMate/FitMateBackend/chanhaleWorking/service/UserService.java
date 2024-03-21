package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.dto.GeneralResponseDto;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.user.repository.UserRepositoryOld;
import FitMate.FitMateBackend.cjjsWorking.dto.myfit.routine.RoutineSetData;
import FitMate.FitMateBackend.cjjsWorking.service.RoutineService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.cjjsWorking.service.authService.ExtraClaims;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.RedisCacheService;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepositoryOld userRepositoryOld;

    @Transactional
    public void register(RegisterForm registerForm){
        User newUser = User.createUser(registerForm, "Customer");
        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepositoryOld.save(newUser);
    }
    @Transactional
    public void registerAdmin(RegisterForm registerForm){
        User newUser = User.createUser(registerForm, "Admin");
        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepositoryOld.save(newUser);
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicatedLoginEmail(String loginEmail){
        return userRepositoryOld.CheckDuplicatedLoginEmail(loginEmail);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserForm updateUserForm) {
        User user = userRepositoryOld.findOne(userId);
        user.updateUser(updateUserForm);

    }

    public boolean checkPassword(Long userId, String password) {
        User user = userRepositoryOld.findOne(userId);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public void updateUserPassword(Long userId, String newPassword) {
        userRepositoryOld.findOne(userId).updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public GeneralResponseDto updateUserPassword(String loginEmail) {
        GeneralResponseDto result = new GeneralResponseDto();
        User user = userRepositoryOld.findByLoginEmail(loginEmail).orElse(null);
        if (user == null) {
            result.setStatus("fail");
            return result;
        }
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.updatePassword(passwordEncoder.encode(newPassword));
        result.setStatus("ok");
        result.setMessage(newPassword);
        return result;
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepositoryOld.deleteUser(userId);
    }

    public User getUserWithId(Long userId) {
        return userRepositoryOld.findOne(userId);
    }

    public String getUserPassword(String loginEmail) {
        User user = userRepositoryOld.findByLoginEmail(loginEmail)
                .filter(u -> u.getLoginEmail().equals(loginEmail))
                .orElse(null);
        if (user == null) {
            return "no Matching email";
        }
        return user.getPassword();
    }


    //🔽🔽🔽 Jwt 🔽🔽🔽
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;
    private final RoutineService routineService;

    @Transactional
    public AuthResponse registerWithJwt(RegisterForm registerForm, String type) {
        User newUser = User.createUserTest(registerForm, passwordEncoder.encode(registerForm.getPassword()), type);

        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepositoryOld.save(newUser);

        /**     2023.08.20 chanhale 수정
         *      newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
         *      userRepository.save(newUser);
         *      부분을 토큰 발행보다 먼저 수행하도록 수정 (토큰 발행시 persist되지 않은 인스턴스에서 getId 시도시 null 반환하여 오류 발생하는 것에 대한 대처)
         */

        //사용자 기본 운동, 보조제 루틴 1개씩 생성
        List<RoutineSetData> workoutRoutine = new ArrayList<>();
        workoutRoutine.add(new RoutineSetData(-1L, 1, "루틴1"));
        workoutRoutine.add(new RoutineSetData(-1L, 2, "루틴2"));
        workoutRoutine.add(new RoutineSetData(-1L, 3, "루틴3"));
        workoutRoutine.add(new RoutineSetData(-1L, 4, "루틴4"));
        routineService.setWorkoutRoutines(newUser, workoutRoutine);
        routineService.saveSupplementRoutine(newUser);

        String accessToken = jwtService.generateAccessToken(newUser, new ExtraClaims(newUser));
        String refreshToken = jwtService.generateRefreshToken(newUser, false);
        redisCacheService.saveToken(refreshToken, false);

        return new AuthResponse(accessToken, refreshToken, false);
    }
}
