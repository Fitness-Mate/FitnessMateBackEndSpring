package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver.Login;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserArgResolverDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.UserDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.mailServer.UuidVerifyingRequestDto;
import FitMate.FitMateBackend.chanhaleWorking.form.user.DeleteUserForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.RegisterForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdatePasswordForm;
import FitMate.FitMateBackend.chanhaleWorking.form.user.UpdateUserForm;
import FitMate.FitMateBackend.chanhaleWorking.service.UserService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.AuthResponse;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * User 생성, 수정 삭제에 관한 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j(topic = "UserController")
public class UserController {
    private final UserService userService;

    @GetMapping("/private")
    public UserDto getMUser(@Login UserArgResolverDto loginUser) {
        if (loginUser == null) {
            return new UserDto();
        } else
            return UserDto.createUserDto(userService.getUserWithId(loginUser.getUserId()));
    }

    @PostMapping("/private")
    public String updateUser(@Login UserArgResolverDto loginUser, @RequestBody UpdateUserForm form) {
        log.info(loginUser.getLoginEmail());
        if (form.getUserName().length()>8 || form.getUserName().length()<3) {
            return "너무 짧은 유저명: 3자리 이상, 8자리 이하";
        }
        userService.updateUser(loginUser.getUserId(), form);
        return "ok";
    }

    @PostMapping("/private/password")
    public String updateUserPassword(@Login UserArgResolverDto loginUser, @RequestBody UpdatePasswordForm form) {
        log.info("old={}, new={}", form.getOldPassword(), form.getNewPassword());
        User user = userService.getUserWithId(loginUser.getUserId());
        if (userService.checkPassword(loginUser.getUserId(), form.getOldPassword())) {
            userService.updateUserPassword(loginUser.getUserId(), form.getNewPassword());
            return "ok";
        }
        return "fail";
    }

    @PostMapping("/private/delete")
    public String deleteUser(@Login UserArgResolverDto loginUser, @RequestBody DeleteUserForm form) {
        log.info(form.getPassword());
        if (loginUser != null) {
            User user = userService.getUserWithId(loginUser.getUserId());
            if (userService.checkPassword(loginUser.getUserId(), form.getPassword())) {
                userService.deleteUser(loginUser.getUserId());
                return "ok";
            }
        }
        return "fail";
    }

    @PostMapping("/auth/verify/email/{userEmail}")
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
