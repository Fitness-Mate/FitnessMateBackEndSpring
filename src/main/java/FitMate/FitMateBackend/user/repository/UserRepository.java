package FitMate.FitMateBackend.user.repository;

import FitMate.FitMateBackend.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginEmail(String loginEmail);
}
