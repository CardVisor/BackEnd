package com.project.cardvisor.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardBenefitComposite is a Querydsl query type for CardBenefitComposite
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCardBenefitComposite extends BeanPath<CardBenefitComposite> {

    private static final long serialVersionUID = -1214570396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardBenefitComposite cardBenefitComposite = new QCardBenefitComposite("cardBenefitComposite");

    public final com.project.cardvisor.vo.QBenefitVO benefitId;

    public final com.project.cardvisor.vo.QCardListVO cardType;

    public QCardBenefitComposite(String variable) {
        this(CardBenefitComposite.class, forVariable(variable), INITS);
    }

    public QCardBenefitComposite(Path<? extends CardBenefitComposite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardBenefitComposite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardBenefitComposite(PathMetadata metadata, PathInits inits) {
        this(CardBenefitComposite.class, metadata, inits);
    }

    public QCardBenefitComposite(Class<? extends CardBenefitComposite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.benefitId = inits.isInitialized("benefitId") ? new com.project.cardvisor.vo.QBenefitVO(forProperty("benefitId"), inits.get("benefitId")) : null;
        this.cardType = inits.isInitialized("cardType") ? new com.project.cardvisor.vo.QCardListVO(forProperty("cardType")) : null;
    }

}

