package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentsVO is a Querydsl query type for PaymentsVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentsVO extends EntityPathBase<PaymentsVO> {

    private static final long serialVersionUID = -1721682146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentsVO paymentsVO = new QPaymentsVO("paymentsVO");

    public final NumberPath<Integer> appliedBenefitId = createNumber("appliedBenefitId", Integer.class);

    public final NumberPath<Integer> benefitAmount = createNumber("benefitAmount", Integer.class);

    public final StringPath currencyCode = createString("currencyCode");

    public final NumberPath<Double> currencyRate = createNumber("currencyRate", Double.class);

    public final DatePath<java.sql.Date> dataInsertDate = createDate("dataInsertDate", java.sql.Date.class);

    public final QMccVO mccCode;

    public final StringPath nation = createString("nation");

    public final NumberPath<Long> payAmount = createNumber("payAmount", Long.class);

    public final DateTimePath<java.sql.Timestamp> payDate = createDateTime("payDate", java.sql.Timestamp.class);

    public final StringPath payId = createString("payId");

    public final StringPath payStore = createString("payStore");

    public final QCardRegInfoVO regId;

    public QPaymentsVO(String variable) {
        this(PaymentsVO.class, forVariable(variable), INITS);
    }

    public QPaymentsVO(Path<? extends PaymentsVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentsVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentsVO(PathMetadata metadata, PathInits inits) {
        this(PaymentsVO.class, metadata, inits);
    }

    public QPaymentsVO(Class<? extends PaymentsVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mccCode = inits.isInitialized("mccCode") ? new QMccVO(forProperty("mccCode")) : null;
        this.regId = inits.isInitialized("regId") ? new QCardRegInfoVO(forProperty("regId"), inits.get("regId")) : null;
    }

}

