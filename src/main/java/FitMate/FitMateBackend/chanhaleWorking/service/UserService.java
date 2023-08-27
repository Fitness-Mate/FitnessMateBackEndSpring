package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.cjjsWorking.service.authService.ExtraClaims;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.RedisCacheService;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void register(RegisterForm registerForm){
        User newUser = User.createUser(registerForm, "Customer");
        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepository.save(newUser);
    }
    @Transactional
    public void registerAdmin(RegisterForm registerForm){
        User newUser = User.createUser(registerForm, "Admin");
        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicatedLoginEmail(String loginEmail){
        return userRepository.CheckDuplicatedLoginEmail(loginEmail);
    }

    @Transactional
    public void updateUser(Long userId, UpdateUserForm updateUserForm) {
        User user = userRepository.findOne(userId);
        user.updateUser(updateUserForm);

    }

    @Transactional
    public void updateUserPassword(Long userId, String newPassword) {
        userRepository.findOne(userId).updatePassword(newPassword);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }

    public User getUserWithId(Long userId) {
        return userRepository.findOne(userId);
    }

    public String getUserPassword(String loginEmail) {
        User user = userRepository.findByLoginEmail(loginEmail)
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

    @Transactional
    public AuthResponse registerWithJwt(RegisterForm registerForm, String type) {
        User newUser = User.createUserTest(registerForm, passwordEncoder.encode(registerForm.getPassword()), type);

        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepository.save(newUser);

        /**     2023.08.20 chanhale 수정
         *      newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
         *      userRepository.save(newUser);
         *      부분을 토큰 발행보다 먼저 수행하도록 수정 (토큰 발행시 persist되지 않은 인스턴스에서 getId 시도시 null 반환하여 오류 발생하는 것에 대한 대처)
         */
        String accessToken = jwtService.generateAccessToken(newUser, new ExtraClaims(newUser));
        String refreshToken = jwtService.generateRefreshToken(newUser, false);
        redisCacheService.saveToken(refreshToken, false);


        return new AuthResponse(accessToken, refreshToken, false);
    }
}
