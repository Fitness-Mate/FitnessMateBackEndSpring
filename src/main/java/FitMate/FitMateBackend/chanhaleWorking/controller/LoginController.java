package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.chanhaleWorking.form.login.LoginForm;
import FitMate.FitMateBackend.chanhaleWorking.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return "okdk";
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
}
