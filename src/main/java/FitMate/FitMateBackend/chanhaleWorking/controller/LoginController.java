package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.chanhaleWorking.form.login.LoginForm;
import FitMate.FitMateBackend.chanhaleWorking.service.LoginService;
import com.amazonaws.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 서비스의 Login 에 관련된 URL 처리하는 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "fail";//"입력 오류";
        }
        log.info("login attempt [{}]",loginForm.getLoginEmail() );


        User loginUser = loginService.login(loginForm.getLoginEmail(),loginForm.getPassword());
        if(loginUser == null){
            return "fail";//"로그인 실패. 아이디와 패스워드를 확인해주세요.";
        }
        HttpSession session = request.getSession();


        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        if(loginUser.getType().equals("Admin"))
            session.setAttribute(SessionConst.LOGIN_ADMIN, loginUser);
        log.info("login success [{}]",loginForm.getLoginEmail() );
        return "ok";
    }

    @PutMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "ok";
    }


    //🔽🔽🔽 Jwt 🔽🔽🔽
    @PostMapping("/auth/jwt/login") //login
    public ResponseEntity<AuthResponse> loginWithJwt(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(loginService.loginWithJwt(loginForm.getLoginEmail(), loginForm.getPassword()));
    }

    @GetMapping("/auth/jwt/logout") //logout
    @PreAuthorize("hasAnyRole('Admin', 'Customer')")
    public void logoutWithJwt(@RequestHeader HttpHeaders header) {
        loginService.logoutWithJwt(Objects.requireNonNull(header.getFirst("authorization")).substring("Bearer ".length()));
    }
}
