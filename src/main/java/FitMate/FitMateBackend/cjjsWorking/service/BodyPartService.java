package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomErrorCode;
import FitMate.FitMateBackend.cjjsWorking.exception.CustomException;
import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BodyPartService {

    private final BodyPartRepository bodyPartRepository;

    @Transactional
    public ResponseEntity<String> saveBodyPart(BodyPartRequest request) {
        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return ResponseEntity.status(400).body(new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST).getDetailMessage());

        BodyPart bodyPart = new BodyPart();
        bodyPart.update(request.getEnglishName(), request.getKoreanName());

        bodyPartRepository.save(bodyPart);

        return ResponseEntity.ok("[" + bodyPart.getKoreanName() + ":" + bodyPart.getEnglishName() + "] 등록 완료");
    }

    @Transactional
    public ResponseEntity<String> updateBodyPart(Long bodyPartId, BodyPartRequest request) {
        if(!this.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return ResponseEntity.status(400).body(new CustomException(CustomErrorCode.BODY_PART_ALREADY_EXIST).getDetailMessage());

        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId);
        findBodyPart.update(request.getEnglishName(), request.getKoreanName());
        return ResponseEntity.ok("[" + findBodyPart.getKoreanName() + ":" + findBodyPart.getEnglishName() + "] 수정 완료");
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
