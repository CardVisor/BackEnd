package com.project.cardvisor;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.InterestRepository;
import com.project.cardvisor.vo.InterestVO;


@SpringBootTest
public class InterestTest {

	@Autowired
	InterestRepository irepo;
	
	@Test
	void f1() {
		 String[] iList = {"주유/교통","생활","쇼핑","문화/교육","외식/카페","여행","사업자"};
		
		IntStream.range(0, iList.length).forEach(i->{
			InterestVO interest = InterestVO.builder()
					.interestType(iList[i])
					.build();
			irepo.save(interest);
		});
	
	
	}
}
