package com.project.cardvisor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BenefitTests {

	@Test
	public void f1() {
		
		LocalDate now = LocalDate.now();
		String[] ageValList = new String[] { "20", "30", "40", "50", "60", "70대 이상" };
		List<Map<String, LocalDate>> ageRangeList = new LinkedList<>();
		
		log.info("1~9"); 
		log.info(now.minusYears(8).withMonth(1).withDayOfMonth(1).toString());
		log.info(now+"");
		log.info("10대 시작"); //10~19
		log.info(now.minusYears(18).withMonth(1).withDayOfMonth(1).toString());
		log.info(now.minusYears(9).withMonth(12).withDayOfMonth(31).toString());
		log.info("20대 시작"); //20~29
		log.info(now.minusYears(28).withMonth(1).withDayOfMonth(1).toString());
		log.info(now.minusYears(19).withMonth(12).withDayOfMonth(31).toString());
		log.info("30대 시작"); //30~39
		log.info(now.minusYears(38).withMonth(1).withDayOfMonth(1)+"");
		log.info(now.minusYears(29).withMonth(12).withDayOfMonth(31)+"");
		log.info("40대 시작");
		log.info( now.minusYears(48).withMonth(1).withDayOfMonth(1).toString());
		log.info(now.minusYears(39).withMonth(12).withDayOfMonth(31)+"");
		log.info("50대 시작");
		log.info(now.minusYears(58).withMonth(1).withDayOfMonth(1).toString());
		log.info(now.minusYears(49).withMonth(12).withDayOfMonth(31).toString());
		log.info("60대 시작");
		log.info( now.minusYears(68).withMonth(1).withDayOfMonth(1).toString());
		log.info(now.minusYears(59).withMonth(12).withDayOfMonth(31)+"");
		log.info("70대 시작"); //70세 이상, 아래의 값 보다 이전
		log.info(now.minusYears(69).withMonth(12).withDayOfMonth(31).toString());

					
		
	}
}
