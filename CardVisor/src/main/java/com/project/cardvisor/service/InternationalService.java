package com.project.cardvisor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.PaymentRepository;

@Service
public class InternationalService {
	

	@Autowired
	PaymentRepository payrep;
	
	//해외 토탈 결제 금액
	public Long getTotalpayment() {
		return payrep.selectTotalOverseasPayment();
	}
	
	//전년 월 대비 올해 월 증감
    public Map<String, Object> getComparePaymentSamePeriod(int month) {
        return payrep.selectDiffPaymentThisYearAndLastYear(month);
    }

	//올해 결제 건수가 제일 많은 나라 (순위 리스트업)
	public List<Map<String, Object>> getHighestOrderPayment() {
		return payrep.selectHighestOrderPayment();
	}

	//월별 국가 결제 상세 정보 리스트
	public List<Map<String, Object>> getNationPaymentsDataList() {
		return payrep.selectNationPaymentsDataList();
	}
}