package FitMate.FitMateBackend.workout.controller;

import FitMate.FitMateBackend.workout.dto.Machine.UserMachineRequest;
import FitMate.FitMateBackend.workout.dto.Machine.UserMachineResponse;
import FitMate.FitMateBackend.workout.service.MachineService;
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

    @PostMapping("machines/list") //부위별 조회 (TEST 완료)
    public ResponseEntity<List<UserMachineResponse>> findMachines(@RequestBody UserMachineRequest request) {
        return ResponseEntity.ok(
                machineService.findWithBodyPart(request.getBodyPartKoreanName()).stream()
                .map(UserMachineResponse::new)
                .collect(Collectors.toList()));
    }
}