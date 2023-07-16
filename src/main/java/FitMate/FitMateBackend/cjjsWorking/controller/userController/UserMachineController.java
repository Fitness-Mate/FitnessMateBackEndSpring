package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserMachineController {

    private final MachineService machineService;

    @PostMapping("machines/list") //부위별 조회 (TEST 완료)
    public List<userMachineResponse> findMachines(@SessionAttribute(name = SessionConst.LOGIN_USER) User user,
                                                  @RequestBody UserMachineRequest request) {
        if(user == null) return null;
        if(request.bodyPartKoreanName == null) {
            return null; //not existing check box
        }

        List<Machine> machines = machineService.findWithBodyPart(request.bodyPartKoreanName);

        return machines.stream()
                .map(userMachineResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    static class UserMachineRequest {
        private List<String> bodyPartKoreanName;
    }

    @Getter
    static class userMachineResponse {
        private String englishName;
        private String koreanName;

        public userMachineResponse(Machine machine) {
            this.englishName = machine.getEnglishName();
            this.koreanName = machine.getKoreanName();
        }
    }
}