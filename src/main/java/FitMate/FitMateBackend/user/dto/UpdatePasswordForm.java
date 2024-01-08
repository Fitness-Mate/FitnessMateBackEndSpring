package FitMate.FitMateBackend.user.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UpdatePasswordForm {
    private String oldPassword;
    private String newPassword;
}
