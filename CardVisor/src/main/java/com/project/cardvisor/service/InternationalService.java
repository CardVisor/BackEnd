package com.project.cardvisor.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.controller.InternationalController.SearchParams;
import com.project.cardvisor.repo.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InternationalService {

	@Autowired
	PaymentRepository payrep;

	static Map<String, Integer> CntMap = new HashMap<>();

	// 해외 토탈 결제 금액
	public Long getTotalpayment() {
		return payrep.selectTotalOverseasPayment();
	}

	// 전년 월 대비 올해 월 증감
	public Map<String, Object> getComparePaymentSamePeriod(int month) {
		return payrep.selectDiffPaymentThisYearAndLastYear(month);
	}

	// 올해 결제 건수가 제일 많은 나라 (순위 리스트업)
	public List<Map<String, Object>> getHighestOrderPayment() {
		return payrep.selectHighestOrderPayment();
	}

	// (차트 데이터) 월별 데이터 추출
	public Map<String, Map<String, Map<String, Object>>> getNationPaymentsDataList(SearchParams params) {
		LocalDate start = YearMonth.parse(params.getPeriod().get("startMonth")).atDay(1);
		LocalDate end = YearMonth.parse(params.getPeriod().get("endMonth")).atDay(1);

		List<Map<String, Object>> payList;
		Map<String, Map<String, Map<String, Object>>> result = new HashMap<>();
		Map<String, Map<String, Map<String, Object>>> result2 = new HashMap<>();
		String oldMonth = "";
		String oldNation = "";

		// 성별NO && 금액NO && 나이NO ==> 필터기능 없음.
		if (params.getGenders().isEmpty() && params.getPriceRange().isEmpty()
				&& params.getAgeGroupsAsNumbers().isEmpty()) {

			payList = payrep.selectNationPaymentsDataList(start, end);

			for (Map<String, Object> pay : payList) {
				String nation = (String) pay.get("nation");
				String month = (String) pay.get("Month");

				initializeResult(result, CntMap, nation, month, oldNation, oldMonth);
				updateResult(result, pay, nation, month);

				int count = Integer.parseInt(pay.get("cnt").toString());
				String KeyValue = pay.get("age_range").toString() + " " + pay.get("cust_gender").toString();
				CntMap.put(KeyValue, count);

				oldMonth = month;
				oldNation = nation;
			}
			handleCountMap(result, CntMap, oldNation, oldMonth);

		} else {

			// filter
			List<String> genders = params.getGenders();
			Long startPrice = params.getStartPrice();
			Long endPrice = params.getEndPrice();
			List<Integer> ageGroups = params.getAgeGroupsAsNumbers();
			List<String> countries = params.getCountries();
			
			if (countries.isEmpty()) {
				payList = payrep.selectNationPaymentsDataList(start, end);
			} else {
				payList = payrep.selectInternationalFilterList(start, end, countries);
			}

			//System.out.println("params.getCountries()" + params.getCountries());
			//payList = payrep.selectInternationalFilterList(start, end, countries);

			for (Map<String, Object> pay : payList) {
				String nation = (String) pay.get("nation");
				String month = (String) pay.get("Month");

				initializeResult(result, CntMap, nation, month, oldNation, oldMonth);
				updateResult(result, pay, nation, month);

				int count = Integer.parseInt(pay.get("cnt").toString());
				String KeyValue = pay.get("age_range").toString() + " " + pay.get("cust_gender").toString();
				CntMap.put(KeyValue, count);

				oldMonth = month;
				oldNation = nation;
			}
			handleCountMap(result, CntMap, oldNation, oldMonth);

			//System.out.println("filterrrr result>>>>>>>>>>>>>>>!!" + result); //추출데이터 
			//System.out.println(">>>>>result: "+result.size());
			
			for (Map.Entry<String, Map<String, Map<String, Object>>> item : result.entrySet()) {
			    String key = item.getKey();
			    Map<String, Map<String, Object>> resultVal = item.getValue();
			    Map<String, Map<String, Object>> resultVal2 = new HashMap<>();
			    
			    for(Map.Entry<String, Map<String, Object>> dateEntry : resultVal.entrySet()) {
			        String dateKey = dateEntry.getKey();
			        Map<String, Object> dateValue = dateEntry.getValue();

			        //System.out.println("날짜: " + dateKey + ", 상세 정보: " + dateValue);
			        //log.info("total_amount:" + endPrice + ":" + dateValue.get("total_amount"));
			       
		        	if(ageGroups.contains( Integer.parseInt((String)dateValue.get("age_range"))) &&
		        			genders.contains( (String)dateValue.get("gender_range")) &&
		        			((Double)dateValue.get("total_amount"))>=  startPrice &&
		        			((Double)dateValue.get("total_amount"))<= endPrice
		        			) {
		        		System.out.println("---------------------"+dateKey);
		        		 System.out.println(dateValue);
		        		 resultVal2.put(dateKey, dateValue) ;
		        	}
			       //System.out.println("----resultVal2-----");
			    }
			    
			    if(resultVal2.size()>0) result2.put(key, resultVal2);
			    //System.out.println(">>>>>result2: "+result2.get(key));
			}

			System.out.println("filtered result2!!!!!!!!!!!!>>>>>>>>>>>>>>>!!" + result2);
			return result2;
		}

		System.out.println("filtered result!!!!!!!!!!!!>>>>>>>>>>>>>>>!!" + result);
		return result;
	}

	private void handleCountMap(Map<String, Map<String, Map<String, Object>>> result, Map<String, Integer> CntMap,
			String oldNation, String oldMonth) {
		if (!oldMonth.equals("")) {
			int maxValue = -1;
			String argmax = "";
			for (String key : CntMap.keySet()) {
				if (maxValue < CntMap.get(key)) {
					argmax = key;
					maxValue = CntMap.get(key);
				}
			}
			String[] parts = argmax.split(" ");
			if (parts.length > 1) {
				result.get(oldNation).get(oldMonth).put("age_range", parts[0]);
				result.get(oldNation).get(oldMonth).put("gender_range", parts[1]);
			}
		}
	}

	private void updateResult(Map<String, Map<String, Map<String, Object>>> result, Map<String, Object> pay,
			String nation, String month) {
		if (result.get(nation).get(month).get("total_payment_count") == null) {
			result.get(nation).get(month).put("total_payment_count", 0);
		}
		if (result.get(nation).get(month).get("total_amount") == null) {
			result.get(nation).get(month).put("total_amount", 0.0);
		}

		int count = Integer.parseInt(pay.get("cnt").toString());
		int oldCount = (int) result.get(nation).get(month).get("total_payment_count");
		result.get(nation).get(month).put("total_payment_count", count + oldCount);

		double amount = Double.parseDouble(pay.get("sum").toString());
		double oldAmount = (double) result.get(nation).get(month).get("total_amount");
		result.get(nation).get(month).put("total_amount", amount + oldAmount);
	}

	private void initializeResult(Map<String, Map<String, Map<String, Object>>> result, Map<String, Integer> CntMap,
			String nation, String month, String oldNation, String oldMonth) {
		if (result.get(nation) == null) {
			result.put(nation, new HashMap<>());
		}
		if (result.get(nation).get(month) == null) {
			result.get(nation).put(month, new HashMap());
			handleCountMap(result, CntMap, oldNation, oldMonth);
			CntMap = new HashMap<>();
		}
	}

}
