package FitMate.FitMateBackend.user.controller;

import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.form.login.LoginForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.service.LoginService;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import FitMate.FitMateBackend.consts.ServiceConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "AuthController")
public class AuthController {
    private final UserService userService;
    private final LoginService loginService;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    //일반 유저 회원가입 V1 (메일 인증 X)
    @PostMapping("/user/auth")
    public ResponseEntity<?> register(
        @RequestBody RegisterForm registerForm
    ) {
        String errMsg = registerForm.validateFields();

        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(errMsg); // errMsg 참고

        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body("아이디 중복"); // 아이디 중복

        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Customer"));
    }

    //일반 유저 회원가입 V2 (메일 인증 O)
    @PostMapping("/user/auth/withuuid")
    public ResponseEntity<?> userRegisterWithUuid(
        @RequestBody RegisterForm registerForm
    ) {
        String errMsg = registerForm.validateFields();

        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(errMsg); // errMsg 참고

        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body("아이디 중복"); // 아이디 중복

        // 메일 인증 uuid 체크 관련 기능
        if (registerForm.getUuid() == null) {
            log.info("uuid 비어있음");
            return ResponseEntity.status(400).body("blank uuid");
        }

        //mail server에 uuid 보내서 메일 인증이 완료돈 사용자인지 검증한다.. 이거구나

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(ServiceConst.MAIL_SERVER_MEDIA_TYPE));

        HttpEntity<UuidVerifyingRequestDto> httpEntity = new HttpEntity<>(new UuidVerifyingRequestDto(registerForm.getLoginEmail(), registerForm.getUuid()), headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ServiceConst.MAIL_SERVER_ADDRESS.concat("/register/verify/uuid"), httpEntity, String.class);

        if (responseEntity.getStatusCode() != HttpStatusCode.valueOf(200) || !responseEntity.getBody().equals("ok")) {
            log.info("error status code responded for /verify/uuid request [{}]",responseEntity.getStatusCode());
            return ResponseEntity.status(400).body(responseEntity.getBody());
        }

        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Customer"));
    }

    //관리자 회원가입
    @PostMapping("/user/admin/register")
    public ResponseEntity<?> adminRegister(
        @RequestBody RegisterForm registerForm
    ) {
        String errMsg = registerForm.validateFields();

        if (!errMsg.equals("ok"))
            return ResponseEntity.status(400).body(null); // errMsg 참고
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return ResponseEntity.status(400).body(null); // 아이디 중복

        return ResponseEntity.ok(userService.registerWithJwt(registerForm, "Admin"));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> loginWithJwt(
        @RequestBody LoginForm loginForm
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            loginService.loginWithJwt(loginForm)
        );
    }

    @GetMapping("/auth/logout")
    public void logoutWithJwt(
        @RequestHeader HttpHeaders header
    ) {
        String refreshToken = JwtService.getToken(header);

        loginService.logoutWithJwt(refreshToken);
    }

    @GetMapping("/auth/refresh") //access token 재발급
    public ResponseEntity<AuthResponse> refresh(
        @RequestHeader HttpHeaders header
    ) {
        String refreshToken = JwtService.getToken(header);
        String accessToken = jwtService.generateAccessTokenWithRefreshToken(refreshToken);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, true));
    }
}
