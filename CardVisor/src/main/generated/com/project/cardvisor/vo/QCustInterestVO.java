package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustInterestVO is a Querydsl query type for CustInterestVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustInterestVO extends EntityPathBase<CustInterestVO> {

    private static final long serialVersionUID = -1405559154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustInterestVO custInterestVO = new QCustInterestVO("custInterestVO");

    public final com.project.cardvisor.composite.QCustInterestComposite id;

    public QCustInterestVO(String variable) {
        this(CustInterestVO.class, forVariable(variable), INITS);
    }

    public QCustInterestVO(Path<? extends CustInterestVO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustInterestVO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustInterestVO(PathMetadata metadata, PathInits inits) {
        this(CustInterestVO.class, metadata, inits);
    }

    public QCustInterestVO(Class<? extends CustInterestVO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.project.cardvisor.composite.QCustInterestComposite(forProperty("id"), inits.get("id")) : null;
    }

}

