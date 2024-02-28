package com.project.cardvisor;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.composite.CustInterestComposite;
import com.project.cardvisor.repo.CustInterestRepository;
import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.repo.InterestRepository;
import com.project.cardvisor.vo.CustInterestVO;
import com.project.cardvisor.vo.InterestVO;


@SpringBootTest
public class CustInterestTest {

	@Autowired
	InterestRepository irepo;

	@Autowired
	CustomerRepository crepo;
	
	@Autowired
	CustInterestRepository custInter;

	@Test
	void f1() {
		crepo.findAll().forEach(cust->{
			int id = new Random().nextInt(1,8);
			InterestVO interst = irepo.findById(id).orElse(null);
			
			CustInterestComposite compoId = CustInterestComposite.builder()
					.custId(cust)
					.interestId(interst)
					.build();
			CustInterestVO vo = CustInterestVO.builder()
					.id(compoId)
					.build();
			/* custInter.save(vo); */
		});
	}
	
	
}
