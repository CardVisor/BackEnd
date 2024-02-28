package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminVO is a Querydsl query type for AdminVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminVO extends EntityPathBase<AdminVO> {

    private static final long serialVersionUID = 1337808080L;

    public static final QAdminVO adminVO = new QAdminVO("adminVO");

    public final StringPath adminId = createString("adminId");

    public final StringPath adminPw = createString("adminPw");

    public final StringPath adminRole = createString("adminRole");

    public QAdminVO(String variable) {
        super(AdminVO.class, forVariable(variable));
    }

    public QAdminVO(Path<? extends AdminVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminVO(PathMetadata metadata) {
        super(AdminVO.class, metadata);
    }

}

