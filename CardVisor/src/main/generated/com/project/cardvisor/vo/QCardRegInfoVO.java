package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardRegInfoVO is a Querydsl query type for CardRegInfoVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardRegInfoVO extends EntityPathBase<CardRegInfoVO> {

    private static final long serialVersionUID = -1802317581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardRegInfoVO cardRegInfoVO = new QCardRegInfoVO("cardRegInfoVO");

    public final StringPath cardNum = createString("cardNum");

    public final QCardListVO cardType;

    public final QCustomerVO custId;

    public final DatePath<java.sql.Date> expireDate = createDate("expireDate", java.sql.Date.class);

    public final DatePath<java.sql.Date> regDate = createDate("regDate", java.sql.Date.class);

    public final StringPath regId = createString("regId");

    public QCardRegInfoVO(String variable) {
        this(CardRegInfoVO.class, forVariable(variable), INITS);
    }

    public QCardRegInfoVO(Path<? extends CardRegInfoVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardRegInfoVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardRegInfoVO(PathMetadata metadata, PathInits inits) {
        this(CardRegInfoVO.class, metadata, inits);
    }

    public QCardRegInfoVO(Class<? extends CardRegInfoVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cardType = inits.isInitialized("cardType") ? new QCardListVO(forProperty("cardType")) : null;
        this.custId = inits.isInitialized("custId") ? new QCustomerVO(forProperty("custId"), inits.get("custId")) : null;
    }

}

