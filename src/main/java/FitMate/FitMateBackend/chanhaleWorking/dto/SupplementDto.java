package FitMate.FitMateBackend.chanhaleWorking.dto;

import FitMate.FitMateBackend.cjjsWorking.service.storageService.S3FileService;
import FitMate.FitMateBackend.consts.ServiceConst;
import FitMate.FitMateBackend.domain.supplement.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 보조제 정보 조회 요청에 사용할 dto
 */
@Data
@NoArgsConstructor
public class SupplementDto {

    private Long id;
    private String englishName;
    private String koreanName;
    private Integer price;
    private Float servings;
    private String description;
    private String marketURL;
    private String supplementType;
    private String imageURL;
    private String flavor;
    private Boolean isCaptain;
    private List<SupplementFlavorDto> otherFlavors;

    // protein, Gainer
    private Float proteinPerServing;
    private Float fatPerServing;
    private Float carbohydratePerServing;
    private String source;

    // AminoAcid
    private Float leucine;
    private Float isoLeucine;
    private Float valine;
    private Float L_Carnitine;
    private Float L_Glutamine;
    private Float L_Alanine;
    private Float L_Lysine;
    private Float methionine;
    private Float phenylalanine;
    private Float threonine;
    private Float histidine;
    private Float tryptophan;

    //Other
    private String contains;

    public SupplementDto(Supplement supplement, List<SupplementFlavorDto> otherFlavors){
        this.id = supplement.getId();
        this.englishName = supplement.getEnglishName();
        this.koreanName = supplement.getKoreanName();
        this.price = supplement.getPrice();
        this.servings = supplement.getServings();
        this.description = supplement.getDescription();
        this.marketURL = supplement.getMarketURL();
        this.flavor = supplement.getFlavor();
        this.imageURL = S3FileService.getAccessURL(ServiceConst.S3_DIR_SUPPLEMENT, supplement.getImageName());
//        this.image = new UrlResource("file:" + FileStoreService.getFullPath(supplement.getImagePath()));
        this.isCaptain = supplement.getIsCaptain();
        this.otherFlavors = otherFlavors;
        if (supplement instanceof Gainer) {
            this.supplementType = "Gainer";
            Gainer sup = (Gainer) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        } else if(supplement instanceof Protein) {
            this.supplementType = "Protein";
            Protein sup = (Protein) supplement;
            this.proteinPerServing = sup.getProteinPerServing();
            this.fatPerServing = sup.getFatPerServing();
            this.carbohydratePerServing = sup.getCarbohydratePerServing();
            this.source = sup.getSource();
        } else if (supplement instanceof AminoAcid) {
            this.supplementType = "AminoAcid";
            AminoAcid sup = (AminoAcid) supplement;
            this.leucine = sup.getLeucine();
            this.isoLeucine = sup.getIsoLeucine();
            this.valine = sup.getValine();
            this.L_Carnitine = sup.getL_Carnitine();
            this.L_Glutamine = sup.getL_Glutamine();
            this.L_Alanine = sup.getL_Alanine();
            this.L_Lysine = sup.getL_Lysine();
            this.methionine = sup.getMethionine();
            this.phenylalanine = sup.getPhenylalanine();
            this.threonine = sup.getThreonine();
            this.histidine = sup.getHistidine();
            this.tryptophan = sup.getTryptophan();
        } else if (supplement instanceof Other) {
            this.supplementType = "Other";
            Other sup = (Other) supplement;
            this.contains = sup.getContains();
        }

    }

}
