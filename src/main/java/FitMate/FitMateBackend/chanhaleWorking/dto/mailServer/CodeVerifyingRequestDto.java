package FitMate.FitMateBackend.chanhaleWorking.dto.mailServer;

import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.CodeVerifyingRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeVerifyingRequestDto {
    private String mailAddress;
    private String verificationCode;

    public static CodeVerifyingRequestDto createCodeVerifyingRequestDto(CodeVerifyingRequestForm form) {
        CodeVerifyingRequestDto result = new CodeVerifyingRequestDto();
        result.mailAddress = form.getMailAddress();
        result.verificationCode = form.getVerificationCode();

        return result;
    }
}
