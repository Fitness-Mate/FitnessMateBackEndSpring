package FitMate.FitMateBackend.cjjsWorking.controller.adminController;

import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartRequest;
import FitMate.FitMateBackend.cjjsWorking.dto.bodyPart.BodyPartsResponse;
import FitMate.FitMateBackend.cjjsWorking.service.BodyPartService;
import FitMate.FitMateBackend.cjjsWorking.service.authService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminBodyPartController {

    private final BodyPartService bodyPartService;

    @PostMapping("/bodyParts") //운동부위 등록 (TEST 완료)
    public ResponseEntity<String> saveBodyPart(@RequestBody BodyPartRequest request) {
        return bodyPartService.saveBodyPart(request);
    }

    @GetMapping("/bodyParts/{bodyPartId}") //운동부위 단일조회 (TEST 완료)
    public ResponseEntity<?> findBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        return bodyPartService.findOne(bodyPartId);
    }

    @GetMapping("/bodyParts/list") //운동부위 전체조회 (TEST 완료)
    public ResponseEntity<BodyPartsResponse> findBodyParts() {
        return ResponseEntity.ok(new BodyPartsResponse(bodyPartService.findAll()));
    }

    @GetMapping("/bodyParts/list/{page}") //운동부위 페이지조회 (TEST 완료)
    public ResponseEntity<?> findBodyParts_page(@PathVariable(value = "page") int page) {
        return bodyPartService.findAll(page);
    }

    @PutMapping("/bodyParts/{bodyPartId}") //운동부위 수정 (TEST 완료)
    public ResponseEntity<String> updateBodyPart(@PathVariable("bodyPartId") Long bodyPartId, @RequestBody BodyPartRequest request) {
        return bodyPartService.updateBodyPart(bodyPartId, request);
    }

    @DeleteMapping("/bodyParts/{bodyPartId}") //운동부위 삭제 (TEST 완료)
    public ResponseEntity<String> removeBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        return bodyPartService.removeBodyPart(bodyPartId);
    }
}