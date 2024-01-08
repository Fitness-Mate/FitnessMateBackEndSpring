package FitMate.FitMateBackend.common.util.smtp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendNewPasswordDto {
    private String mailAddress;
    private String newPassword;
}
