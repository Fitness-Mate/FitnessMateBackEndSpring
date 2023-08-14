package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminMachineController {

    private final MachineService machineService;

    @PostMapping("admin/machines") //운동 기구 생성
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> saveMachine(@RequestBody MachineRequest request) {
        return machineService.saveMachine(request);
    }

    @GetMapping("admin/machines/{machineId}") //단건조회
    public ResponseEntity<?> findMachine(@PathVariable("machineId") Long machineId) {
        return machineService.findOne(machineId);
    }

    @GetMapping("admin/machines/list/{page}") //batch 단위 조회
    public ResponseEntity<?> findMachines_page(@PathVariable("page") int page) {
        return machineService.findAll(page);
    }

    @PutMapping("admin/machines/{machineId}") //운동 기구 수정
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> updateMachine(@PathVariable("machineId") Long machineId, @RequestBody MachineRequest request) {
        return machineService.updateMachine(machineId, request);
    }

    @DeleteMapping("admin/machines/{machineId}") //삭제
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> deleteMachine(@PathVariable("machineId") Long machineId) {
        return machineService.removeMachine(machineId);
    }
}