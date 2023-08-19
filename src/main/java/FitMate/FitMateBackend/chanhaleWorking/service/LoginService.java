package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.form.login.LoginForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomException;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.cjjsWorking.service.authService.ExtraClaims;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.RedisCacheService;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User login(String loginEmail, String password) {
        return userRepository.findByLoginEmail(loginEmail)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
    @Transactional(readOnly = true)
    public User adminLogin(String loginEmail, String password){
        return userRepository.findByLoginEmail(loginEmail)
                .filter(u -> u.getPassword().equals(password))
                .filter(u -> u.getType().equals("Admin"))
                .orElse(null);
    }

    //🔽🔽🔽 Jwt 🔽🔽🔽
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RedisCacheService redisCacheService;

    public AuthResponse loginWithJwt(LoginForm form) { //user login
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    form.getLoginEmail(), form.getPassword()
                )
        );
        User user = userRepository.findByLoginEmail(form.getLoginEmail()).orElse(null);
        // 아이디 존재여부 체크, 비밀번호 대조 추가
        if (user == null || !passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            return null;
        }
        String accessToken = jwtService.generateAccessToken(user, new ExtraClaims(user));
        String refreshToken =jwtService.generateRefreshToken(user, form.isRememberMe());
        redisCacheService.saveToken(refreshToken, form.isRememberMe());

        log.info("login attempt! AccessToken: [{}], RefreshToken: [{}], User: [{}]",
                accessToken,
                refreshToken,
                JwtService.getLoginEmail(accessToken));
        return new AuthResponse(accessToken, refreshToken, form.isRememberMe());
    }

    public void logoutWithJwt(String refreshToken) {
        if(!redisCacheService.isExist(refreshToken)) {
            throw new CustomException(CustomErrorCode.ALREADY_LOGOUT_EXCEPTION);
        }
        redisCacheService.removeToken(refreshToken);
    }
}
