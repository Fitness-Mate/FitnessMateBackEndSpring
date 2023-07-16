package FitMate.FitMateBackend.cjjsWorking.service;

import FitMate.FitMateBackend.cjjsWorking.repository.BodyPartRepository;
import FitMate.FitMateBackend.domain.BodyPart;
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

    //Overloading
    public List<BodyPart> findAll() {
        return bodyPartRepository.findAll();
    }
    public List<BodyPart> findAll(int page) {
        return bodyPartRepository.findAll(page);
    }
    //Overloading

    public BodyPart findOne(Long bodyPartId) {
        return bodyPartRepository.findById(bodyPartId);
    }

    @Transactional
    public Long removeBodyPart(Long bodyPartId) {
        BodyPart findBodyPart = bodyPartRepository.findById(bodyPartId);
        bodyPartRepository.remove(findBodyPart);
        return bodyPartId;
    }

}
