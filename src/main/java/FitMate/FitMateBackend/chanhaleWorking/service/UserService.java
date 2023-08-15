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


    //🔽🔽🔽 Jwt 🔽🔽🔽
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;

    @Transactional
    public AuthResponse registerWithJwt(RegisterForm registerForm, String type) {
        User newUser = User.createUserTest(registerForm, passwordEncoder.encode(registerForm.getPassword()), type);

        String token = jwtService.generateToken(newUser, new ExtraClaims(newUser));
        redisCacheService.saveUser(token, newUser);

        newUser.addBodyDataHistory(BodyData.createBodyData(registerForm.getBodyDataForm()));
        userRepository.save(newUser);

        return new AuthResponse(token);
    }
}
