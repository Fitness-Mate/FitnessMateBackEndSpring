package FitMate.FitMateBackend.domain.supplement;

import FitMate.FitMateBackend.chanhaleWorking.form.supplement.SupplementForm;
import FitMate.FitMateBackend.consts.ServiceConst;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("BCAA")
@NoArgsConstructor
public class BCAA extends Supplement {


    private Float leucine;
    private Float IsoLeucine;
    private Float Valine;
    public BCAA(SupplementForm supplementForm) {
        super(supplementForm);
    }
    public void updateFields(SupplementForm supplementForm) {
        super.updateFields(supplementForm);
    }

    @Override
    public String createIntroduction() {
        return "{" +
                "Name: \""+ ServiceConst.RECOMMEND_PREFIX +this.getId()+ ServiceConst.RECOMMEND_SUFFIX+"\", "+
                "type: "+this.getType()+", "+
                "price: "+(this.getPrice() / this.getServings())+"won, "+
                "leucine: 2.5g, "+
                "Isoleucine: 1.25g, "+
                "Valine: 1.25g}"
                ;
    }
}
