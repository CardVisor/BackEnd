package com.project.cardvisor.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJobListVO is a Querydsl query type for JobListVO
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobListVO extends EntityPathBase<JobListVO> {

    private static final long serialVersionUID = -1959367044L;

    public static final QJobListVO jobListVO = new QJobListVO("jobListVO");

    public final NumberPath<Integer> jobId = createNumber("jobId", Integer.class);

    public final StringPath jobType = createString("jobType");

    public QJobListVO(String variable) {
        super(JobListVO.class, forVariable(variable));
    }

    public QJobListVO(Path<? extends JobListVO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJobListVO(PathMetadata metadata) {
        super(JobListVO.class, metadata);
    }

}

