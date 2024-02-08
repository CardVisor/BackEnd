package com.project.cardvisor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.InternationalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/international")
public class InternationalController {

	final InternationalService iservice;
	
	//해외 토탈 결제 금액
	@GetMapping("/totalPayment")
	public Long getTotalpayment() {
		return iservice.getTotalpayment();
	}
	
	//전년 월 대비 올해 월 증감
	@GetMapping("/comparePaymentSamePeriod")
	public Long getComparePaymentSamePeriod(@RequestParam(name = "month", required = false) Integer month) {
		month = 2;
		return iservice.getComparePaymentSamePeriod(month);
	}
	
	//올해 건수가 제일 많은 나라 (순위 리스트업)
	@GetMapping("/highestOrderPayment")
	public List<Map<String, Object>> getHighestOrderPayment() {
		List<Map<String, Object>> result = iservice.getHighestOrderPayment();
		//System.out.println(">>>>>>>>>>>>"+ result.size());
		return result;
	}
}


/*
해야 할 일
- 해외 토탈 결제 금액  --> 올해로 수정해야함
- 전년 월 대비 올해 월 증감 (+/-)
- 올해 건수가 제일 많은 나라 (순위 리스트업)

---------------------------------------------------------------

- 세계지도 chart 사용하여 해외 결제 시각화 정보 제공
- 색상 → 결제 건수
- 월별로 선택가능하도록 → 22년 23년 24년도 필터
- 월 **슬라이드**
- 디테일 정보 (월단위)
    - 건수
    - 총금액
    - 제일 많은 연령대
*/