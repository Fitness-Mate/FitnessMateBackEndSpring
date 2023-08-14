package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.GetAllBodyPartResponse;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminBodyPartController {

    private final BodyPartService bodyPartService;

    @PostMapping("admin/bodyParts") //운동 부위 등록 (TEST 완료 - jwt)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> saveBodyPart(@RequestBody BodyPartRequest request) {
        return bodyPartService.saveBodyPart(request);
    }

    @PutMapping("admin/bodyParts/{bodyPartId}") //운동 부위 수정
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> updateBodyPart(@PathVariable("bodyPartId") Long bodyPartId, @RequestBody BodyPartRequest request) {
        return bodyPartService.updateBodyPart(bodyPartId, request);
    }

    @GetMapping("admin/bodyParts/{bodyPartId}") //운동 부위 단일 조회
    public ResponseEntity<?> findBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        return bodyPartService.findOne(bodyPartId);
    }

    @GetMapping("admin/bodyParts/list") //운동 부위 전체 조회
    public ResponseEntity<GetAllBodyPartResponse> findBodyParts() {
        return bodyPartService.findAll();
    }

    @GetMapping("admin/bodyParts/list/{page}") //운동 부위 페이지 조회
    public ResponseEntity<?> findBodyParts_page(@PathVariable(value = "page") int page) {
        return bodyPartService.findAll(page);
    }

    @DeleteMapping("admin/bodyParts/{bodyPartId}") //운동 부위 삭제
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> removeBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        return bodyPartService.removeBodyPart(bodyPartId);
    }
}