package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.GetMachineResponse;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineDto;
import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminMachineController {

    private final MachineService machineService;
    private final BodyPartService bodyPartService;

    @PostMapping("admin/machines") //생성 (TEST 완료)
    public String saveMachine(@SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                              @RequestBody MachineRequest request) {
        if(admin == null) return null;
        if(!machineService.checkMachineNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return "이미 존재하는 운동기구입니다. 이름을 확인해주세요.";

        Machine machine = new Machine();
        machine.update(request.getEnglishName(), request.getKoreanName());

        for (String koreanName : request.getBodyPartKoreanName()) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(koreanName);
            if(findBodyPart == null) return koreanName + "운동 부위를 찾을 수 없습니다.";

            findBodyPart.addMachine(machine);
            machine.getBodyParts().add(findBodyPart);
        }

        return machineService.saveMachine(machine).toString();
    }

    @PutMapping("admin/machines/{machineId}") //수정 (TEST 완료)
    public String updateMachine(@PathVariable("machineId") Long machineId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                              @RequestBody MachineRequest request) {
        if(admin == null) return null;
        if(!machineService.checkMachineNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return "이미 존재하는 운동기구입니다. 이름을 확인해주세요.";

        machineService.updateMachine(machineId, request.getEnglishName(), request.getKoreanName(), request.getBodyPartKoreanName());
        return machineId.toString();
    }

    @GetMapping("admin/machines/{machineId}") //단건조회 (TEST 완료)
    public GetMachineResponse findMachine(@PathVariable("machineId") Long machineId,
                                          @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        Machine findMachine = machineService.findOne(machineId);
        return new GetMachineResponse(findMachine.getEnglishName(), findMachine.getKoreanName(),
                findMachine.getBodyParts());
    }

    @GetMapping("admin/machines/list/{page}") //batch 단위 조회 (TEST 완료)
    public List<MachineDto> findMachines_page(@PathVariable("page") int page,
                                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        List<Machine> findMachines = machineService.findAll(page);

        return findMachines.stream()
                .map(MachineDto::new)
                .collect(Collectors.toList());
    }

    @DeleteMapping("admin/machines/{machineId}") //삭제 (TEST 완료)
    public String deleteMachine(@PathVariable("machineId") Long machineId,
                              @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        machineService.removeMachine(machineId);
        return machineId.toString();
    }
}