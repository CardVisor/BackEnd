package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardBenefitVO is a Querydsl query type for CardBenefitVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardBenefitVO extends EntityPathBase<CardBenefitVO> {

    private static final long serialVersionUID = -235936536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardBenefitVO cardBenefitVO = new QCardBenefitVO("cardBenefitVO");

    public final com.project.cardvisor.composite.QCardBenefitComposite id;

    public QCardBenefitVO(String variable) {
        this(CardBenefitVO.class, forVariable(variable), INITS);
    }

    public QCardBenefitVO(Path<? extends CardBenefitVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardBenefitVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardBenefitVO(PathMetadata metadata, PathInits inits) {
        this(CardBenefitVO.class, metadata, inits);
    }

    public QCardBenefitVO(Class<? extends CardBenefitVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.project.cardvisor.composite.QCardBenefitComposite(forProperty("id"), inits.get("id")) : null;
    }

}

