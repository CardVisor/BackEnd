package com.project.cardvisor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.JobRepository;
import com.project.cardvisor.vo.JobListVO;

@SpringBootTest
public class JobListTest {

	@Autowired
	JobRepository jrepo;
	
	@Test
	void f1() {
	
		String[] joblist = {"직장인","공무원","전문직","프리랜서","개인사업자","법인사업자","대학생","전업주부","무직"};
		
		for(int i=0; i<joblist.length; i++) {
			String job = joblist[i];
			JobListVO j1 = JobListVO.builder().jobType(job).build();
			jrepo.save(j1);
		}
	}
}
