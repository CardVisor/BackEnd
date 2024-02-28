package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCardListVO is a Querydsl query type for CardListVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardListVO extends EntityPathBase<CardListVO> {

    private static final long serialVersionUID = 1400249215L;

    public static final QCardListVO cardListVO = new QCardListVO("cardListVO");

    public final NumberPath<Integer> cardAnnualFee = createNumber("cardAnnualFee", Integer.class);

    public final StringPath cardImgUrl = createString("cardImgUrl");

    public final StringPath cardName = createString("cardName");

    public final NumberPath<Integer> cardType = createNumber("cardType", Integer.class);

    public QCardListVO(String variable) {
        super(CardListVO.class, forVariable(variable));
    }

    public QCardListVO(Path<? extends CardListVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCardListVO(PathMetadata metadata) {
        super(CardListVO.class, metadata);
    }

}

