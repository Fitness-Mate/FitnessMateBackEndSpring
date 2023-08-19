package FitMate.FitMateBackend.chanhaleWorking.controller;

import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementListDto;
import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementSearchForm;
import FitMate.FitMateBackend.chanhaleWorking.service.FileStoreService;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import FitMate.FitMateBackend.domain.supplement.SupplementType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@ResponseBody
@RequestMapping("/supplements")
public class SupplementController {
    private final SupplementService supplementService;

    @GetMapping("/{supplementId}")
    public SupplementDto getSingleSupplement(@PathVariable("supplementId") Long supplementId){
        Supplement supplement = supplementService.findSupplementById(supplementId);
        if (supplement == null) {
            return new SupplementDto();
        }
        return new SupplementDto(supplement);
    }

//    @GetMapping("/image/{supplementId}")
//    public ResponseEntity<Resource> DownloadImage(@PathVariable("supplementId") Long supplementId, HttpServletResponse response) throws MalformedURLException {
//        Supplement supplement = supplementService.findSupplementById(supplementId);
//        // 경로의 파일에 접근해서 파일을 스트림으로 반환한다.
//        //TODO
//        // 파일 접근 경로 만드는 메서드로 변경하기
//        UrlResource resource = new UrlResource("file:" + FileStoreService.getFullPath(supplement.getImageName()));
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
//                .body(resource);
//    }

    @GetMapping("/list/{pageNum}")
    public List<SupplementListDto> getSupplementList(@PathVariable("pageNum") Long pageNum){
        List<Supplement> supplementList = supplementService.getSupplementBatch(pageNum);
        List<SupplementListDto> supplementDtoList = new ArrayList<>();
        log.info("supplement.createIntroduction()");
        for (Supplement supplement : supplementList) {
            supplementDtoList.add(new SupplementListDto(supplement));
            log.info(supplement.createIntroduction());
        }
        return supplementDtoList;
    }

    @GetMapping("/type")
    public List<SupplementType> getSupplementTypes() {

        return Arrays.stream(SupplementType.values()).toList();
    }

    @PostMapping("/search/list/{pageNum}")
    public List<SupplementListDto> searchSupplements(@PathVariable("pageNum") Long page, @RequestBody SupplementSearchForm form){
        List<Supplement> supplementList = supplementService.searchSupplementBatch(page, form);
        List<SupplementListDto> supplementListDtoList = new ArrayList<>();
        for (Supplement supplement : supplementList) {
            supplementListDtoList.add(new SupplementListDto(supplement));
            log.info(supplement.createIntroduction());
        }
        return supplementListDtoList;
    }
}
