package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver.Login;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserDto;
import FitMate.FitMateBackend.chanhaleWorking.form.user.DeleteUserForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdatePasswordForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * User 생성, 수정 삭제에 관한 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@ResponseBody
public class UserController {
    private final UserService userService;

    @PostMapping
    public String register(@RequestBody RegisterForm registerForm) {
        log.info("REGISTER [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        if (!errMsg.equals("ok"))
            return errMsg;
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return "아이디 중복";
        userService.register(registerForm);
        return "ok";
    }

    @GetMapping
    public UserDto getMUser(@Login User loginUser) {
        if (loginUser == null) {
            return new UserDto();
        } else
            return UserDto.createUserDto(loginUser);
    }

    @PostMapping("/admin/register")
    public String adminRegister(@RequestBody RegisterForm registerForm) {
        log.info("REGISTER [{}] [{}]", registerForm.getUserName(), registerForm.getSex());
        String errMsg = registerForm.validateFields();
        if (errMsg != "ok")
            return errMsg;
        if (userService.checkDuplicatedLoginEmail(registerForm.getLoginEmail()))
            return "아이디 중복";
        userService.registerAdmin(registerForm);
        return "ok";
    }

    @PutMapping
    public String updateUser(@Login User loginUser, @RequestBody UpdateUserForm form) {
        log.info(loginUser.getLoginEmail());
        userService.updateUser(loginUser, form);
        return "ok";
    }

    @PostMapping("/password")
    public String updateUserPassword(@Login User loginUser, @RequestBody UpdatePasswordForm form) {
        log.info("old={}, new={}", form.getOldPassword(), form.getNewPassword());
        if (loginUser.getPassword().equals(form.getOldPassword())) {
            userService.updateUserPassword(loginUser, form.getNewPassword());
            return "ok";
        }
        return "fail";
    }



    @PostMapping("/delete")
    public String deleteUser(@Login User loginUser, @RequestBody DeleteUserForm form) {
        log.info(form.getPassword());
        if (loginUser != null) {
            if (loginUser.getPassword().equals(form.getPassword())) {
                userService.deleteUser(loginUser);
                return "ok";
            }
        }
        return "fail";
    }

    @PostMapping("/verify/email/{userEmail}")
    public String verifyUserId(@PathVariable("userEmail")String userEmail) {
        String regexPattern = "^(.+)@(\\S+)$";
        if (userService.checkDuplicatedLoginEmail(userEmail)) {
            return "아이디 중복";
        } else if (!userEmail.matches(regexPattern)){
            return "형식에 맞지 않는 이메일 주소";
        }
        return "ok";
    }
}
