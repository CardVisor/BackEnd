package com.project.cardvisor.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustInterestComposite is a Querydsl query type for CustInterestComposite
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCustInterestComposite extends BeanPath<CustInterestComposite> {

    private static final long serialVersionUID = -1670010010L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustInterestComposite custInterestComposite = new QCustInterestComposite("custInterestComposite");

    public final com.project.cardvisor.vo.QCustomerVO custId;

    public final com.project.cardvisor.vo.QInterestVO interestId;

    public QCustInterestComposite(String variable) {
        this(CustInterestComposite.class, forVariable(variable), INITS);
    }

    public QCustInterestComposite(Path<? extends CustInterestComposite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustInterestComposite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustInterestComposite(PathMetadata metadata, PathInits inits) {
        this(CustInterestComposite.class, metadata, inits);
    }

    public QCustInterestComposite(Class<? extends CustInterestComposite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.custId = inits.isInitialized("custId") ? new com.project.cardvisor.vo.QCustomerVO(forProperty("custId"), inits.get("custId")) : null;
        this.interestId = inits.isInitialized("interestId") ? new com.project.cardvisor.vo.QInterestVO(forProperty("interestId")) : null;
    }

}

