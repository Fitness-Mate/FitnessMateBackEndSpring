package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.cjjsWorking.service.storageService.RedisCacheService;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
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
    private final RedisCacheService redisCacheService;

    public AuthResponse loginWithJwt(String loginEmail, String password) { //user login
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginEmail, password
                )
        );
        User user = userRepository.findByLoginEmail(loginEmail).orElse(null);
        String token = jwtService.generateToken(user);
        redisCacheService.saveUser(token, user);

        return new AuthResponse(token);
    }

    public void logoutWithJwt(String token) { //user logout
        redisCacheService.removeUser(token);
    }
}
