package FitMate.FitMateBackend.cjjsWorking.exception;

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