package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInterestVO is a Querydsl query type for InterestVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInterestVO extends EntityPathBase<InterestVO> {

    private static final long serialVersionUID = -638532645L;

    public static final QInterestVO interestVO = new QInterestVO("interestVO");

    public final NumberPath<Integer> interestId = createNumber("interestId", Integer.class);

    public final StringPath interestType = createString("interestType");

    public QInterestVO(String variable) {
        super(InterestVO.class, forVariable(variable));
    }

    public QInterestVO(Path<? extends InterestVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInterestVO(PathMetadata metadata) {
        super(InterestVO.class, metadata);
    }

}

