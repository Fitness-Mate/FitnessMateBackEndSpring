package FitMate.FitMateBackend.chanhaleWorking.dto.mailServer;

import FitMate.FitMateBackend.chanhaleWorking.form.mailServer.UuidVerifyingRequestForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UuidVerifyingRequestDto {
    private String mailAddress;
    private String uuid;

    public static UuidVerifyingRequestDto createUuidVerifyingRequestDto(UuidVerifyingRequestForm form) {
        UuidVerifyingRequestDto result = new UuidVerifyingRequestDto();
        result.mailAddress = form.getMailAddress();
        result.uuid = form.getUuid();
        return result;
    }
}
