package FitMate.FitMateBackend.domain.recommendation;

import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.BodyData;
import FitMate.FitMateBackend.domain.EnglishPurpose;
import FitMate.FitMateBackend.domain.Purpose;
import FitMate.FitMateBackend.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Slf4j
@DiscriminatorValue("Supplement")
public class SupplementRecommendation extends Recommendation {

    private Long monthlyBudget; // 예산
    private String purposes = ""; // purposes 들을 and 로 묶은 것

    @OneToMany
    List<RecommendedSupplement> recommendedSupplements = new ArrayList<>();



    public void addRecommendSupplements(RecommendedSupplement recommendedSupplement) {
        recommendedSupplements.add(recommendedSupplement);
    }

    public static SupplementRecommendation createSupplementRecommendation(BodyData bodyData, User user, List<Purpose> purposes, Long monthlyBudget) {
        SupplementRecommendation supplementRecommendation = new SupplementRecommendation();
        supplementRecommendation.setBodyData(bodyData);
        supplementRecommendation.setUser(user);
        Long idx = 0L;
        log.info(purposes.get(0).name());
        for (Purpose purpose : purposes) {
            idx++;
            supplementRecommendation.purposes.concat(purpose.name());
            if (idx < purposes.size()) {
                supplementRecommendation.purposes.concat(" and ");
            }
        }
        supplementRecommendation.monthlyBudget = monthlyBudget;
        supplementRecommendation.setRecommendationType("Supplement");
        String qString = "suggest up to 3 supplements in this list. For a ";
        qString = qString.concat(user.getSex() == "남성" ? "man" : "woman").concat(" who is ");
        qString = qString.concat(bodyData.describe());
        log.info(bodyData.describe());
        qString = qString.concat(user.getSex() == "남성" ? " His" : " Her")
                .concat(" purpose is ");
        idx = 0L;
        for (Purpose purpose : purposes) {
            idx++;
            qString = qString.concat(EnglishPurpose.values()[purpose.ordinal()].name());
            if (idx < purposes.size()) {
                qString = qString.concat(" and ");
            }
        }
        qString = qString.concat(". your budget is ").concat(monthlyBudget.toString()).concat("Won.");
        supplementRecommendation.setQueryText(qString);
        return supplementRecommendation;
    }
}
