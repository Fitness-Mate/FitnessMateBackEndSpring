package FitMate.FitMateBackend.domain.supplement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAminoAcid is a Querydsl query type for AminoAcid
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAminoAcid extends EntityPathBase<AminoAcid> {

    private static final long serialVersionUID = 91437432L;

    public static final QAminoAcid aminoAcid = new QAminoAcid("aminoAcid");

    public final QSupplement _super = new QSupplement(this);

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final StringPath englishName = _super.englishName;

    //inherited
    public final StringPath flavor = _super.flavor;

    public final NumberPath<Float> histidine = createNumber("histidine", Float.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath imageName = _super.imageName;

    //inherited
    public final BooleanPath isCaptain = _super.isCaptain;

    public final NumberPath<Float> isoLeucine = createNumber("isoLeucine", Float.class);

    //inherited
    public final StringPath koreanName = _super.koreanName;

    public final NumberPath<Float> L_Alanine = createNumber("L_Alanine", Float.class);

    public final NumberPath<Float> l_Alanine = createNumber("l_Alanine", Float.class);

    public final NumberPath<Float> L_Carnitine = createNumber("L_Carnitine", Float.class);

    public final NumberPath<Float> l_Carnitine = createNumber("l_Carnitine", Float.class);

    public final NumberPath<Float> L_Glutamine = createNumber("L_Glutamine", Float.class);

    public final NumberPath<Float> l_Glutamine = createNumber("l_Glutamine", Float.class);

    public final NumberPath<Float> L_Lysine = createNumber("L_Lysine", Float.class);

    public final NumberPath<Float> l_Lysine = createNumber("l_Lysine", Float.class);

    public final NumberPath<Float> leucine = createNumber("leucine", Float.class);

    //inherited
    public final StringPath marketURL = _super.marketURL;

    public final NumberPath<Float> methionine = createNumber("methionine", Float.class);

    public final NumberPath<Float> phenylalanine = createNumber("phenylalanine", Float.class);

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Float> servings = _super.servings;

    public final NumberPath<Float> threonine = createNumber("threonine", Float.class);

    public final NumberPath<Float> tryptophan = createNumber("tryptophan", Float.class);

    //inherited
    public final EnumPath<SupplementType> type = _super.type;

    public final NumberPath<Float> valine = createNumber("valine", Float.class);

    public QAminoAcid(String variable) {
        super(AminoAcid.class, forVariable(variable));
    }

    public QAminoAcid(Path<? extends AminoAcid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAminoAcid(PathMetadata metadata) {
        super(AminoAcid.class, metadata);
    }

}

