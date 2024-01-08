package FitMate.FitMateBackend.user.service;

import FitMate.FitMateBackend.user.dto.LoginForm;
import FitMate.FitMateBackend.user.repository.UserRepository;
import FitMate.FitMateBackend.common.exception.errorcodes.AuthErrorCode;
import FitMate.FitMateBackend.common.exception.exceptions.AuthException;
import FitMate.FitMateBackend.common.util.authService.AuthResponse;
import FitMate.FitMateBackend.common.util.authService.ExtraClaims;
import FitMate.FitMateBackend.common.util.authService.JwtService;
import FitMate.FitMateBackend.common.util.storageService.RedisCacheService;
import FitMate.FitMateBackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try { //ID, PW 검증
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            form.getLoginEmail(), form.getPassword()
                    )
            );
        } catch(BadCredentialsException e) {
            throw new AuthException(AuthErrorCode.AUTHENTICATION_EXCEPTION);
        }

        User user = userRepository.findByLoginEmail(form.getLoginEmail()).orElse(null);
        if(user == null)
            throw new AuthException(AuthErrorCode.AUTHENTICATION_EXCEPTION);

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
            throw new AuthException(AuthErrorCode.ALREADY_LOGOUT_EXCEPTION);
        }
        redisCacheService.removeToken(refreshToken);
    }
}
