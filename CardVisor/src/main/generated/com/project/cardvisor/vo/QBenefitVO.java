package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBenefitVO is a Querydsl query type for BenefitVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBenefitVO extends EntityPathBase<BenefitVO> {

    private static final long serialVersionUID = 2139902488L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBenefitVO benefitVO = new QBenefitVO("benefitVO");

    public final StringPath benefitDetail = createString("benefitDetail");

    public final NumberPath<Integer> benefitId = createNumber("benefitId", Integer.class);

    public final NumberPath<Double> benefitPct = createNumber("benefitPct", Double.class);

    public final QInterestVO interestId;

    public final StringPath mccCode = createString("mccCode");

    public QBenefitVO(String variable) {
        this(BenefitVO.class, forVariable(variable), INITS);
    }

    public QBenefitVO(Path<? extends BenefitVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBenefitVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBenefitVO(PathMetadata metadata, PathInits inits) {
        this(BenefitVO.class, metadata, inits);
    }

    public QBenefitVO(Class<? extends BenefitVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.interestId = inits.isInitialized("interestId") ? new QInterestVO(forProperty("interestId")) : null;
    }

}

