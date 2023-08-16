package FitMate.FitMateBackend.chanhaleWorking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserArgResolverDto {
    Long userId;
    String loginEmail;

    public UserArgResolverDto(Long userId, String loginEmail) {
        this.userId = userId;
        this.loginEmail = loginEmail;
    }
}
