package FitMate.FitMateBackend.cjjsWorking.exception;

import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.AuthException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.CustomException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.JwtFilterException;
import FitMate.FitMateBackend.cjjsWorking.exception.exceptions.RecommendException;
import FitMate.FitMateBackend.cjjsWorking.exception.response.AuthErrorResponse;
import FitMate.FitMateBackend.cjjsWorking.exception.response.CustomErrorResponse;
import FitMate.FitMateBackend.cjjsWorking.exception.response.JwtFilterErrorResponse;
import FitMate.FitMateBackend.cjjsWorking.exception.response.RecommendErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getCustomErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(403).body(new CustomErrorResponse(e.getCustomErrorCode(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(JwtFilterException.class) //JwtFilterException은 Handler에서 동작하지 않음. JwtExceptionFilter에서 동작
    public ResponseEntity<JwtFilterErrorResponse> handleJwtFilterException(JwtFilterException e) {
        return ResponseEntity.status(403).body(new JwtFilterErrorResponse(e.getJwtFilterErrorCode(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthException.class)
    public ResponseEntity<AuthErrorResponse> handleAuthException(AuthException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}", e.getAuthErrorCode(),
                request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(403).body(new AuthErrorResponse(e.getAuthErrorCode(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RecommendException.class)
    public ResponseEntity<RecommendErrorResponse> handleRecommendException(RecommendException e, HttpServletRequest request) {
        log.error("ERROR: {}, URL: {}, MESSAGE: {}, STATUS: {}", e.getRecommendErrorCode(),
                request.getRequestURI(), e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(new RecommendErrorResponse(e.getRecommendErrorCode(), e.getMessage()));
    }
}