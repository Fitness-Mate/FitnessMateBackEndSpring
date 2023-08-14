package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
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

    public AuthResponse loginWithJwt(String loginEmail, String password) { //user login
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginEmail, password
                )
        );
        User user = userRepository.findByLoginEmail(loginEmail).orElse(null);
        // 아이디 존재여부 체크, 비밀번호 대조 추가
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        String token = jwtService.generateToken(user);
        redisCacheService.saveUser(token, user);
        log.info("login attempt! Token: [{}], User: [{}]",
                token,
                getUserWithToken(token).getUserName());
        return new AuthResponse(token);
    }

    public User getUserWithToken(String token) {
        return redisCacheService.findUser(token);
    }

    public void logoutWithJwt(String token) { //user logout
        redisCacheService.removeUser(token);
    }
}
