package FitMate.FitMateBackend.chanhaleWorking.service;

import FitMate.FitMateBackend.chanhaleWorking.config.ChatGptConfig;
import FitMate.FitMateBackend.chanhaleWorking.form.recommendation.SupplementRecommendationForm;
import FitMate.FitMateBackend.chanhaleWorking.repository.RecommendedSupplementRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRecommendationRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.SupplementRepository;
import FitMate.FitMateBackend.chanhaleWorking.repository.UserRepository;
import FitMate.FitMateBackend.cjjsWorking.service.apiService.DeepLTranslateService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.User;
import FitMate.FitMateBackend.domain.recommendation.RecommendedSupplement;
import FitMate.FitMateBackend.domain.recommendation.SupplementRecommendation;
import FitMate.FitMateBackend.domain.supplement.Supplement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SupplementRecommendationService {
    private final SupplementRecommendationRepository supplementRecommendationRepository;
    private final UserRepository userRepository;
    private final SupplementRepository supplementRepository;
    private final RecommendedSupplementRepository recommendedSupplementRepository;
    private final DeepLTranslateService deepLTranslateService;

    @Transactional
    public Long createSupplementRecommendation(Long userId, SupplementRecommendationForm supplementRecommendationForm) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }

        SupplementRecommendation supplementRecommendation = SupplementRecommendation
                .createSupplementRecommendation(user.getBodyDataHistory().get(0), user, supplementRecommendationForm.getPurpose(), supplementRecommendationForm.getMonthlyBudget());
        supplementRecommendationRepository.save(supplementRecommendation);
        return supplementRecommendation.getId();
    }

    public SupplementRecommendation findById(Long userId, Long supplementRecommendationId) {
        return supplementRecommendationRepository.findById(userId, supplementRecommendationId);
    }

  /*  @Transactional
    public void updateGptResponse(Long userId, Long recommendationId, String response) throws Exception {
        SupplementRecommendation supplementRecommendation = supplementRecommendationRepository.findById(userId, recommendationId);
        if (supplementRecommendation == null)
            return;

        int numStart = -1;
        int numEnd = -1;
        while (true) {
            numStart = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart+1);
            if (numStart == -1) {
                break;
            }
            numEnd = response.indexOf(ServiceConst.RECOMMEND_SUFFIX, numEnd+1);
            Long number = Long.parseLong(response.substring(numStart + ServiceConst.RECOMMEND_PREFIX.length(), numEnd));
            int strEnd = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart + 1);
            if (strEnd == -1) {
                strEnd = response.length()-1;
            }
            String str = response.substring(numEnd + ServiceConst.RECOMMEND_SUFFIX.length(), strEnd);
            String strKor = deepLTranslateService.sendRequest(str);
            Supplement supplement = supplementRepository.findById(number);
            if (supplement == null) {
                continue;
            }
            RecommendedSupplement recommendedSupplement = RecommendedSupplement.createRecommendedSupplement(supplement, str, strKor);
            recommendedSupplementRepository.save(recommendedSupplement);
            supplementRecommendation.addRecommendSupplements(recommendedSupplement);
        }
    }*/
       @Transactional
        public void updateGptResponse(Long userId, Long recommendationId, String response) throws Exception {
            SupplementRecommendation supplementRecommendation = supplementRecommendationRepository.findById(userId, recommendationId);
            if (supplementRecommendation == null)
                return;

            int numStart = -1;
            int numEnd = -1;
            int strStart = -1;
            int strEnd = -1;
            int numStartKor = -1;
            int strStartKor = -1;
            int strEndKor = -1;
            response = response.replaceAll("\n", "|");
            String responseKor = deepLTranslateService.sendRequest(response);
            while (true) {
                numStart = response.indexOf(ServiceConst.RECOMMEND_PREFIX, numStart+1);
                numStartKor = responseKor.indexOf(ServiceConst.RECOMMEND_PREFIX, numStartKor+1);
                if (numStart == -1) {
                    break;
                }
                numEnd = response.indexOf(ServiceConst.RECOMMEND_SUFFIX, numEnd+1);
                Long number = Long.parseLong(response.substring(numStart + ServiceConst.RECOMMEND_PREFIX.length(), numEnd));
                strStart = response.indexOf("-", numStart + 1);
                strEnd = response.indexOf("||", numStart + 1);
                strStartKor = responseKor.indexOf("-", numStartKor + 1);
                strEndKor = responseKor.indexOf("||", numStartKor+1);
                if (strEnd == -1) {
                    strEnd = response.length()-1;
                }
                if (strEndKor == -1) {
                    strEndKor = responseKor.length()-1;
                }
                String str = response.substring(strStart+1, strEnd);
                String strKor = responseKor.substring(strStartKor+1, strEndKor);
                Supplement supplement = supplementRepository.findById(number);
                if (supplement == null) {
                    continue;
                }
                RecommendedSupplement recommendedSupplement = RecommendedSupplement.createRecommendedSupplement(supplement, str, strKor);
                recommendedSupplementRepository.save(recommendedSupplement);
                supplementRecommendation.addRecommendSupplements(recommendedSupplement);
            }
        }
    public SupplementRecommendation getSupplementRecommendation(Long userId, Long supplementRecommendationId) {
        return supplementRecommendationRepository.findById(userId, supplementRecommendationId);
    }

    public List<SupplementRecommendation> getSupplementRecommendationBatch(Long userId, Long pageNumber) {
        return supplementRecommendationRepository.getBatch(userId, pageNumber);
    }
}
