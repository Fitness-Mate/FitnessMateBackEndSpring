package FitMate.FitMateBackend.chanhaleWorking.controller;


import FitMate.FitMateBackend.chanhaleWorking.config.argumentresolver.Login;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementRecommendationDto;
import FitMate.FitMateBackend.chanhaleWorking.dto.SupplementRecommendationListDto;
import FitMate.FitMateBackend.chanhaleWorking.form.recommendation.SupplementRecommendationForm;
import FitMate.FitMateBackend.chanhaleWorking.service.ChatGptService;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementRecommendationService;
import FitMate.FitMateBackend.chanhaleWorking.service.SupplementService;
import FitMate.FitMateBackend.cjjsWorking.service.apiService.DeepLTranslateService;
import FitMate.FitMateBackend.domain.Purpose;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.recommendation.SupplementRecommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/recommendation/supplement")
@RequiredArgsConstructor
public class SupplementRecommendationController {
    private final SupplementRecommendationService supplementRecommendationService;
    private final SupplementService supplementService;
    private final ChatGptService chatGptService;
    private final DeepLTranslateService deepLTranslateService;

    @PostMapping
    public Long getRecommendedText(@Login User user, @RequestBody SupplementRecommendationForm form) throws Exception {
        Long recommendationId = supplementRecommendationService.createSupplementRecommendation(user.getId(), form);
        log.info("purpose:[{}][{}]", form.getMonthlyBudget(), form.getPurpose().get(0));
        String question = "I have these supplements.".concat(supplementService.getSupplementString());
        SupplementRecommendation supplementRecommendation = supplementRecommendationService.findById(user.getId(), recommendationId);
        question = question.concat(supplementRecommendation.getQueryText());
        log.info(question);
        chatGptService.sendRequest(user.getId(), supplementRecommendation.getId(), question);
        return recommendationId;
    }

    @GetMapping("/history/{supplementRecommendationId}")
    public SupplementRecommendationDto getRecommendation(@Login User user, @PathVariable("supplementRecommendationId") Long supplementRecommendationId) throws Exception {
        SupplementRecommendation sr = supplementRecommendationService.getSupplementRecommendation(user.getId(), supplementRecommendationId);
        if (sr == null) {
            return new SupplementRecommendationDto();
        }
        // 로그인 유저가 해당 supplementRecommendation 의 소유자가 아님.
        if (!Objects.equals(sr.getBodyData().getUser().getId(), user.getId())) {
            return new SupplementRecommendationDto();
        }
        return SupplementRecommendationDto.createSupplementRecommendationDto(sr);
    }

    @GetMapping("/history/list/{pageNum}")
    public List<SupplementRecommendationListDto> getRecommendationBatch(@Login User user, @PathVariable("pageNum") Long pageNum) throws Exception {
        // User 소유의 supplementRecommendation 인지 확인하는 기능 필요
        List<SupplementRecommendationListDto> result = new ArrayList<>();
        List<SupplementRecommendation> sList = supplementRecommendationService.getSupplementRecommendationBatch(user.getId(), pageNum);
        for (SupplementRecommendation supplementRecommendation : sList) {
            result.add(SupplementRecommendationListDto.createSupplementRecommendationDto(supplementRecommendation));
        }
        return result;
    }

    @GetMapping("/purposes")
    public List<String> getPurposes() {
        List<String> result = new ArrayList<>();
        for (Purpose p : Purpose.values()) {
            result.add(p.name());
        }
        return result;
    }
}
