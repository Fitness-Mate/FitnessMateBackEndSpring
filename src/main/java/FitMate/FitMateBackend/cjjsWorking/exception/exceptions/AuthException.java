package FitMate.FitMateBackend.cjjsWorking.exception.exceptions;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.CustomErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private CustomErrorCode customErrorCode;
    private String message;

    public AuthException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage());
        this.customErrorCode = customErrorCode;
        this.message = customErrorCode.getStatusMessage();
    }
}
