package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository;

    @Transactional
    public Long saveBodyPart(BodyPart bodyPart) {
        bodyPartRepository.save(bodyPart);
        return bodyPart.getId();
    }

    @Transactional
    public Long updateBodyPart(Long bodyPartId, String englishName, String koreanName) {
        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId);
        findBodyPart.update(englishName, koreanName);
        return bodyPartId;
    }

    public BodyPart findOne(Long bodyPartId) {
        return bodyPartRepository.findById(bodyPartId);
    }
    public boolean checkBodyPartNameDuplicate(String koreanName, String englishName) {
        BodyPart bp1 = bodyPartRepository.findByKoreanName(koreanName).orElse(null);
        BodyPart bp2 = bodyPartRepository.findByEnglishName(englishName).orElse(null);
        return (bp1 == null && bp2 == null);
    }

    public BodyPart findByKoreanName(String koreanName) {
        return bodyPartRepository.findByKoreanName(koreanName).orElse(null);
    }

    //Overloading
    public List<BodyPart> findAll() {
        return bodyPartRepository.findAll();
    }
    public List<BodyPart> findAll(int page) {
        return bodyPartRepository.findAll(page);
    }
    //Overloading

    @Transactional
    public Long removeBodyPart(Long bodyPartId) {
        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId);

        //remove related machine
        List<Machine> machines = findBodyPart.getMachines();
        for (Machine machine : machines) {
            machine.getBodyParts().remove(findBodyPart);
        }

        //remove related workout
        List<Workout> workouts = findBodyPart.getWorkouts();
        for (Workout workout : workouts) {
            workout.getBodyParts().remove(findBodyPart);
        }
        bodyPartRepository.remove(findBodyPart);
        return bodyPartId;
    }

}
