package FitMate.FitMateBackend.cjjsWorking.exception.exceptions;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.AuthErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.CustomErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private AuthErrorCode authErrorCode;
    private String message;

    public AuthException(AuthErrorCode authErrorCode) {
        super(authErrorCode.getStatusMessage());
        this.authErrorCode = authErrorCode;
        this.message = authErrorCode.getStatusMessage();
    }
}
