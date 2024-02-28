package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMccVO is a Querydsl query type for MccVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMccVO extends EntityPathBase<MccVO> {

    private static final long serialVersionUID = 781148558L;

    public static final QMccVO mccVO = new QMccVO("mccVO");

    public final StringPath ctgName = createString("ctgName");

    public final StringPath mccCode = createString("mccCode");

    public QMccVO(String variable) {
        super(MccVO.class, forVariable(variable));
    }

    public QMccVO(Path<? extends MccVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMccVO(PathMetadata metadata) {
        super(MccVO.class, metadata);
    }

}

