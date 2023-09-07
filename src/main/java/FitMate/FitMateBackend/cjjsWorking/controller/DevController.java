package FitMate.FitMateBackend.cjjsWorking.controller;

import FitMate.FitMateBackend.cjjsWorking.dto.Machine.MachineRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevController {

    private final BodyPartService bodyPartService;
    private final MachineService machineService;

    @PostMapping("bodyParts/create")
    @PreAuthorize("hasAuthority('Admin')")
    public void createBodyParts(@RequestBody DevBodyPartsRequest request) {
        for (BodyPartRequest req : request.bodyParts) {
            bodyPartService.saveBodyPart(req);
        }
    }

    @PostMapping("machines/create")
    @PreAuthorize("hasAuthority('Admin')")
    public void createMachines(@RequestBody DevMachineRequest request) {
        System.out.println(request);
        for (MachineRequest req : request.machines) {
            machineService.saveMachine(req);
        }
    }

    @Data
    static class DevMachineRequest {
        private List<MachineRequest> machines;
    }

    @Data
    static class DevBodyPartsRequest {
        private List<BodyPartRequest> bodyParts;
    }
}
