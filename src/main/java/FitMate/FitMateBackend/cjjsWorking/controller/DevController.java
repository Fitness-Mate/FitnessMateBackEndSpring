package FitMate.FitMateBackend.cjjsWorking.controller;

import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevController {

    private final BodyPartService bodyPartService;
    private final BodyPartRepository bodyPartRepository;
    private final MachineService machineService;

    @PostMapping("bodyParts/create")
    public void createBodyParts(@RequestBody DevBodyPartsRequest request) {
        for (DevBodyPartName bodyPart : request.bodyParts) {
            BodyPart bp = new BodyPart();
            bp.update(bodyPart.englishName, bodyPart.koreanName);
            bodyPartService.saveBodyPart(bp);
        }
    }
    @PostMapping("machines/create")
    public void createMachines(@RequestBody DevMachineRequest request) {
        for (DevMachines m : request.machines) {
            Machine machine = new Machine();
            machine.update(m.englishName, m.koreanName);

            for (String name : m.bodyPartKoreanName) {
                BodyPart findBP = bodyPartRepository.findByKoreanName(name);
                machine.getBodyParts().add(findBP);
                findBP.getMachines().add(machine);
            }

            machineService.saveMachine(machine);
        }
    }

    @Data
    static class DevMachineRequest {
        private List<DevMachines> machines;
    }

    @Data
    static class DevMachines {
        private String englishName;
        private String koreanName;
        private List<String> bodyPartKoreanName;
    }

    @Data
    static class DevBodyPartsRequest {
        private List<DevBodyPartName> bodyParts;
    }

    @Data
    static class DevBodyPartName {
        private String englishName;
        private String koreanName;
    }
}
