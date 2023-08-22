package FitMate.FitMateBackend.chanhaleWorking.form.mailServer;

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
