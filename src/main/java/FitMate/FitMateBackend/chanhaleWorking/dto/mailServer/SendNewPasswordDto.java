package FitMate.FitMateBackend.chanhaleWorking.dto.mailServer;

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
