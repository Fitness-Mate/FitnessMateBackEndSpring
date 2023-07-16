package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
    @Transactional(readOnly = true)
    public User adminLogin(String loginId, String password){
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .filter(u -> u.getType().equals("Admin"))
                .orElse(null);
    }

}
