package FitMate.FitMateBackend.cjjsWorking.service.authService;

import FitMate.FitMateBackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraClaims {
    private Long userId;

    public ExtraClaims(User user) {
        this.userId = user.getId();
    }
}
