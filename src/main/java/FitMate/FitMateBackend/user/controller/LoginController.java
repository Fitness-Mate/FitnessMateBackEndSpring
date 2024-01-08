package FitMate.FitMateBackend.user.controller;

import FitMate.FitMateBackend.common.util.authService.AuthResponse;
import FitMate.FitMateBackend.user.dto.LoginForm;
import FitMate.FitMateBackend.user.service.LoginService;
import FitMate.FitMateBackend.common.util.authService.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 서비스의 Login 에 관련된 URL 처리하는 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;

//    @PostMapping("/login")
//    @ResponseBody
//    public String login(@RequestBody LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
//        if (bindingResult.hasErrors()){
//            return "fail";//"입력 오류";
//        }
//        log.info("login attempt [{}]",loginForm.getLoginEmail() );
//
//
//        User loginUser = loginService.login(loginForm.getLoginEmail(),loginForm.getPassword());
//        if(loginUser == null){
//            return "fail";//"로그인 실패. 아이디와 패스워드를 확인해주세요.";
//        }
//        HttpSession session = request.getSession();
//
//
//        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
//        if(loginUser.getType().equals("Admin"))
//            session.setAttribute(SessionConst.LOGIN_ADMIN, loginUser);
//        log.info("login success [{}]",loginForm.getLoginEmail() );
//        return "ok";
//    }
//
//    @PutMapping("/logout")
//    @ResponseBody
//    public String logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return "ok";
//    }


    //🔽🔽🔽 Jwt 🔽🔽🔽
    @PostMapping("/auth/login") //login
    public ResponseEntity<AuthResponse> loginWithJwt(@RequestBody LoginForm loginForm) {
        log.info("login attempt [{}]",loginForm.getLoginEmail() );
        return ResponseEntity.ok(loginService.loginWithJwt(loginForm));
    }

    @GetMapping("/auth/logout") //logout
    public void logoutWithJwt(@RequestHeader HttpHeaders header) {
        String refreshToken = JwtService.getToken(header);

        log.info("logout attempt! Token: [{}], User: [{}]",
                refreshToken,
                JwtService.getLoginEmail(refreshToken));

        loginService.logoutWithJwt(refreshToken);
    }

    @GetMapping("/auth/refresh") //access token 재발급
    public ResponseEntity<AuthResponse> refresh(@RequestHeader HttpHeaders header) {
        String refreshToken = JwtService.getToken(header);
        String accessToken = jwtService.generateAccessTokenWithRefreshToken(refreshToken);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, true));
    }
}
