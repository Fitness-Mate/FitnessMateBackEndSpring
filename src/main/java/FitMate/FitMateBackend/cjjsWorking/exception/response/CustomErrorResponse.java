package FitMate.FitMateBackend.cjjsWorking.exception.response;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    private CustomErrorCode status;
    private String statusMessage;
}
