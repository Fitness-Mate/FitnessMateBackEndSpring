package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.GetMachineResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMachineController {

    private final MachineService machineService;

    @PostMapping("/machines") //운동기구 생성 (TEST 완료)
    public ResponseEntity<String> saveMachine(@RequestBody MachineRequest request) {
        return machineService.saveMachine(request);
    }

    @GetMapping("/machines/{machineId}") //운동기구 단일조회 (TEST 완료)
    public ResponseEntity<?> findMachine(@PathVariable("machineId") Long machineId) {
        return ResponseEntity.ok(new GetMachineResponse(machineService.findOne(machineId)));
    }

    @GetMapping("/machines/list/{page}") //운동기구 페이지조회 (TEST 완료)
    public ResponseEntity<?> findMachines_page(@PathVariable("page") int page) {
        return machineService.findAll(page);
    }

    @PutMapping("/machines/{machineId}") //운동기구 수정 (TEST 완료)
    public ResponseEntity<String> updateMachine(@PathVariable("machineId") Long machineId, @RequestBody MachineRequest request) {
        return machineService.updateMachine(machineId, request);
    }

    @DeleteMapping("/machines/{machineId}") //운동기구 삭제 (TEST 완료)
    public ResponseEntity<String> deleteMachine(@PathVariable("machineId") Long machineId) {
        return machineService.removeMachine(machineId);
    }
}