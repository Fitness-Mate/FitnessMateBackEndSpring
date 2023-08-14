package FitMate.FitMateBackend.cjjsWorking.controller.userController;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.UserMachineRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.UserMachineResponse;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserMachineController {

    private final MachineService machineService;

    @PostMapping("machines/list") //부위별 조회
    public ResponseEntity<List<UserMachineResponse>> findMachines(@RequestBody UserMachineRequest request) {
        return ResponseEntity.ok(
                machineService.findWithBodyPart(request.getBodyPartKoreanName()).stream()
                .map(UserMachineResponse::new)
                .collect(Collectors.toList()));
    }
}