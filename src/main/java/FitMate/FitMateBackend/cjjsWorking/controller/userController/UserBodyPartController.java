package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.controller.adminController.AdminBodyPartController;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.AllBodyPartResponseDto;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.apiService.DeepLTranslateService;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserBodyPartController {

    private final BodyPartService bodyPartService;

    @GetMapping("bodyParts/all") // 전체조회 (TEST 완료)
    public AllBodyPartResponseDto findBodyParts(@SessionAttribute(name = SessionConst.LOGIN_USER) User user) {
        if(user == null) return null;

        List<BodyPart> findBodyParts = bodyPartService.findAll();
        return new AllBodyPartResponseDto(findBodyParts);
    }
}
