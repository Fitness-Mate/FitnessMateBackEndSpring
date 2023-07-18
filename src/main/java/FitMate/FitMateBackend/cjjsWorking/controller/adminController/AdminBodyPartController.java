package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartDto;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartResponseDto;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.GetAllBodyPartResponse;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.consts.SessionConst;
import FitMate.FitMateBackend.domain.BodyPart;
import FitMate.FitMateBackend.domain.Machine;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.Workout;
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
public class AdminBodyPartController {

    private final BodyPartService bodyPartService;

    @PostMapping("admin/bodyParts") //운동 부위 정보 등록 (TEST 완료)
    public String saveBodyPart(@SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                             @RequestBody BodyPartRequest request) {
        if(admin == null) return null;
        if(!bodyPartService.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return "이미 존재하는 운동 부위입니다. 이름을 확인해주세요.";

        BodyPart bodyPart = new BodyPart();
        bodyPart.update(request.getEnglishName(), request.getKoreanName());

        return bodyPartService.saveBodyPart(bodyPart).toString();
    }

    @PutMapping("admin/bodyParts/{bodyPartId}") //운동 부위 정보 수정 (TEST 완료)
    public String updateBodyPart(@PathVariable("bodyPartId") Long bodyPartId,
                               @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin,
                               @RequestBody BodyPartRequest request) {
        if(admin == null) return null;
        if(!bodyPartService.checkBodyPartNameDuplicate(request.getKoreanName(), request.getEnglishName()))
            return "이미 존재하는 운동 부위입니다. 이름을 확인해주세요.";

        bodyPartService.updateBodyPart(bodyPartId, request.getEnglishName(), request.getKoreanName());
        return bodyPartId.toString();
    }

    @GetMapping("admin/bodyParts/list") //운동 부위 전체 검색 (TEST 완료)
    public GetAllBodyPartResponse findBodyPartAll(@SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        List<BodyPart> findBodyParts = bodyPartService.findAll();
        return new GetAllBodyPartResponse(findBodyParts);
    }

    @GetMapping("admin/bodyParts/list/{page}") //batch 단위 조회 (TEST 완료)
    public List<BodyPartDto> findBodyParts_page(@PathVariable(value = "page") int page,
                                                @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        List<BodyPart> findBodyParts = bodyPartService.findAll(page);

        return findBodyParts.stream()
                .map(b -> new BodyPartDto(b))
                .collect(Collectors.toList());
    }

    @GetMapping("admin/bodyParts/{bodyPartId}") //운동 부위 단일 조회 (TEST 완료)
    public BodyPartResponseDto findBodyPart(@PathVariable("bodyPartId") Long bodyPartId,
                                            @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;

        BodyPart findBodyPart = bodyPartService.findOne(bodyPartId);
        return new BodyPartResponseDto(findBodyPart.getEnglishName(), findBodyPart.getKoreanName());
    }

    @DeleteMapping("admin/bodyParts/{bodyPartId}") //운동 부위 삭제 (TEST 완료)
    public Long removeBodyPart(@PathVariable("bodyPartId") Long bodyPartId,
                               @SessionAttribute(name = SessionConst.LOGIN_ADMIN) User admin) {
        if(admin == null) return null;
        bodyPartService.removeBodyPart(bodyPartId);
        return bodyPartId;
    }
}