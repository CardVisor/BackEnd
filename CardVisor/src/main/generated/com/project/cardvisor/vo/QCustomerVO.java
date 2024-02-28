package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerVO is a Querydsl query type for CustomerVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerVO extends EntityPathBase<CustomerVO> {

    private static final long serialVersionUID = -620806257L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerVO customerVO = new QCustomerVO("customerVO");

    public final DatePath<java.sql.Date> custBirth = createDate("custBirth", java.sql.Date.class);

    public final StringPath custEmail = createString("custEmail");

    public final ComparablePath<Character> custGender = createComparable("custGender", Character.class);

    public final StringPath custId = createString("custId");

    public final StringPath custName = createString("custName");

    public final StringPath custPhone = createString("custPhone");

    public final StringPath custSalary = createString("custSalary");

    public final QJobListVO jobId;

    public QCustomerVO(String variable) {
        this(CustomerVO.class, forVariable(variable), INITS);
    }

    public QCustomerVO(Path<? extends CustomerVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerVO(PathMetadata metadata, PathInits inits) {
        this(CustomerVO.class, metadata, inits);
    }

    public QCustomerVO(Class<? extends CustomerVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.jobId = inits.isInitialized("jobId") ? new QJobListVO(forProperty("jobId")) : null;
    }

}

