package FitMate.FitMateBackend.cjjsWorking.controller;

import FitMate.FitMateBackend.cjjsWorking.form.WorkoutForm;
import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.MachineService;
import FitMate.FitMateBackend.cjjsWorking.service.cloudService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevController {

    private final BodyPartService bodyPartService;
    private final MachineService machineService;
    private final S3FileService s3FileService;

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
                BodyPart findBP = bodyPartService.findByKoreanName(name);
                machine.getBodyParts().add(findBP);
                findBP.getMachines().add(machine);
            }

            machineService.saveMachine(machine);
        }
    }

    @PostMapping("s3/upload")
    public String s3UploadTest(@ModelAttribute WorkoutForm form) {
        try {
            String filename = s3FileService.uploadImage(ServiceConst.S3_DIR_WORKOUT, form.getImage());
            System.out.println(filename);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
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
