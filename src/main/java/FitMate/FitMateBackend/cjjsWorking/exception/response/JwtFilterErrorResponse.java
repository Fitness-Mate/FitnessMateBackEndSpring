package FitMate.FitMateBackend.cjjsWorking.exception.response;

import FitMate.FitMateBackend.cjjsWorking.exception.errorcodes.JwtFilterErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtFilterErrorResponse {
    private JwtFilterErrorCode status;
    private String statusMessage;
}
