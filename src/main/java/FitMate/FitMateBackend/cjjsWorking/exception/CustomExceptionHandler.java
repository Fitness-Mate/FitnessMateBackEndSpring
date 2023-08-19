package FitMate.FitMateBackend.cjjsWorking.exception;

import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.AuthException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.JwtFilterException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(403).body(new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(JwtFilterException.class) //JwtFilterException은 Handler에서 동작하지 않음. JwtExceptionFilter에서 동작
    public ResponseEntity<CustomErrorResponse> handleJwtFilterException(JwtFilterException e, HttpServletRequest request) {
//        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
//                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(403).body(new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthException(AuthException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(403).body(new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }


}