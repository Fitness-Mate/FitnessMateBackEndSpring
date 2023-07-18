package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.cjjsWorking.repository.MachineRepository;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final BodyPartService bodyPartService;

    @Transactional
    public Long saveMachine(Machine machine) {
        machineRepository.save(machine);
        return machine.getId();
    }

    @Transactional
    public String updateMachine(Long machineId, String englishName, String koreanName, List<String> bodyPartKoreanName) {
        Machine findMachine = machineRepository.findById(machineId);
        findMachine.update(englishName, koreanName);

        for (BodyPart bodyPart : findMachine.getBodyParts()) {
            bodyPart.removeMachine(findMachine);
        }
        findMachine.getBodyParts().clear();

        for (String name : bodyPartKoreanName) {
            BodyPart findBodyPart = bodyPartService.findByKoreanName(name);
            if(findBodyPart == null) return bodyPartKoreanName + "을 찾을 수 없습니다.";

            findBodyPart.addMachine(findMachine);
            findMachine.getBodyParts().add(findBodyPart);
        }

        return machineId.toString();
    }

    //Overloading
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }
    public List<Machine> findAll(int page) {
        return machineRepository.findAll(page);
    }
    //Overloading

    public Machine findOne(Long machineId) {
        return machineRepository.findById(machineId);
    }

    public List<Machine> findWithBodyPart(List<String> bodyPartKoreanName) {
        return machineRepository.findWithBodyPart(bodyPartKoreanName);
    }

    @Transactional
    public Long removeMachine(Long machineId) {
        Machine findMachine = machineRepository.findById(machineId);
        machineRepository.remove(findMachine);
        return machineId;
    }
}
