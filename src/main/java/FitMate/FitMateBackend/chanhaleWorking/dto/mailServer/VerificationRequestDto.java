package FitMate.FitMateBackend.chanhaleWorking.dto.mailServer;

import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.VerificationRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequestDto {
    private String mailAddress;

    public static VerificationRequestDto createVerificationRequestDto(VerificationRequestForm form) {
        VerificationRequestDto result = new VerificationRequestDto();
        result.mailAddress = form.getMailAddress();
        return result;
    }
}
