package FitMate.FitMateBackend.cjjsWorking.exception.exceptions;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.JwtFilterErrorCode;
import lombok.Getter;

@Getter
public class JwtFilterException extends RuntimeException {

    private JwtFilterErrorCode jwtFilterErrorCode;
    private String message;

    public JwtFilterException(JwtFilterErrorCode jwtFilterErrorCode) {
        super(jwtFilterErrorCode.getStatusMessage());
        this.jwtFilterErrorCode = jwtFilterErrorCode;
        this.message = jwtFilterErrorCode.getStatusMessage();
    }
}
