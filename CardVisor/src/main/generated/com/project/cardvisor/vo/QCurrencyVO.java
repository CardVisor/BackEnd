package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCurrencyVO is a Querydsl query type for CurrencyVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurrencyVO extends EntityPathBase<CurrencyVO> {

    private static final long serialVersionUID = -129077502L;

    public static final QCurrencyVO currencyVO = new QCurrencyVO("currencyVO");

    public final StringPath currencyCode = createString("currencyCode");

    public final DateTimePath<java.sql.Timestamp> currencyDate = createDateTime("currencyDate", java.sql.Timestamp.class);

    public final StringPath currencyFlagUrl = createString("currencyFlagUrl");

    public final NumberPath<Integer> currencyId = createNumber("currencyId", Integer.class);

    public final StringPath currencyNation = createString("currencyNation");

    public final NumberPath<Double> currencyRate = createNumber("currencyRate", Double.class);

    public QCurrencyVO(String variable) {
        super(CurrencyVO.class, forVariable(variable));
    }

    public QCurrencyVO(Path<? extends CurrencyVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurrencyVO(PathMetadata metadata) {
        super(CurrencyVO.class, metadata);
    }

}

