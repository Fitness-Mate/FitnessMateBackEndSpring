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
import com.amazonaws.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminBodyPartController {

    private final BodyPartService bodyPartService;

    @PostMapping("admin/bodyParts") //운동 부위 정보 등록 (TEST 완료 - jwt)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> saveBodyPart(@RequestBody BodyPartRequest request) {
        return bodyPartService.saveBodyPart(request);
    }

    @PutMapping("admin/bodyParts/{bodyPartId}") //운동 부위 정보 수정
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> updateBodyPart(@PathVariable("bodyPartId") Long bodyPartId, @RequestBody BodyPartRequest request) {
        //만약 없는 운동 부위 id에 대한 삭제 요청이 들어왔을 시 Exception
        return bodyPartService.updateBodyPart(bodyPartId, request);
    }

    @GetMapping("admin/bodyParts/list") //운동 부위 전체 검색
    public GetAllBodyPartResponse findBodyPartAll() {
        return new GetAllBodyPartResponse(bodyPartService.findAll());
    }

    @GetMapping("admin/bodyParts/list/{page}") //batch 단위 조회
    public List<BodyPartDto> findBodyParts_page(@PathVariable(value = "page") int page) {
        return bodyPartService.findAll(page).stream()
                .map(BodyPartDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("admin/bodyParts/{bodyPartId}") //운동 부위 단일 조회
    public BodyPartResponseDto findBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        //만약 없는 운동 부위 id에 대한 조회 요청이 들어왔을 시 Exception
        return new BodyPartResponseDto(bodyPartService.findOne(bodyPartId));
    }

    @DeleteMapping("admin/bodyParts/{bodyPartId}") //운동 부위 삭제
    @PreAuthorize("hasAuthority('Admin')")
    public Long removeBodyPart(@PathVariable("bodyPartId") Long bodyPartId) {
        //만약 없는 운동 부위 id에 대한 삭제 요청이 들어왔을 시 Exception
        bodyPartService.removeBodyPart(bodyPartId);
        return bodyPartId;
    }
}