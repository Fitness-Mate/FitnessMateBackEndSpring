package FitMate.FitMateBackend.common.util.smtp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeVerifyingRequestForm {
    private String mailAddress;
    private String verificationCode;

}
