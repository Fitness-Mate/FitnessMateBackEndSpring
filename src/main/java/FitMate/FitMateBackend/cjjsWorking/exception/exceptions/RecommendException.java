package FitMate.FitMateBackend.cjjsWorking.exception.exceptions;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.RecommendErrorCode;
import lombok.Getter;

@Getter
public class RecommendException extends RuntimeException {

    private RecommendErrorCode recommendErrorCode;
    private String message;
    private int status;

    public RecommendException(RecommendErrorCode recommendErrorCode, int status) {
        super(recommendErrorCode.getStatusMessage());
        this.recommendErrorCode = recommendErrorCode;
        this.message = recommendErrorCode.getStatusMessage();
        this.status = status;
    }
}