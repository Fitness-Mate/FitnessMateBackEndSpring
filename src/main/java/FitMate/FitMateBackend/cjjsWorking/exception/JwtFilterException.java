package FitMate.FitMateBackend.cjjsWorking.exception;

import lombok.Getter;

@Getter
public class JwtFilterException extends RuntimeException {

    private CustomErrorCode customErrorCode;
    private String message;

    public JwtFilterException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage());
        this.customErrorCode = customErrorCode;
        this.message = customErrorCode.getStatusMessage();
    }
}
