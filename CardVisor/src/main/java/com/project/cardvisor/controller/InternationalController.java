package com.project.cardvisor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.InternationalService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/international")
public class InternationalController {

	final InternationalService iservice;

	/*
	 * private List<Map<String, Object>> nationData;
	 * 
	 * @PostMapping("/nation") public List<Map<String, Object>>
	 * receiveData(@RequestBody List<Map<String, Object>> nation) {
	 * log.info("Received nation data: {}", nation); nationData = nation; return
	 * nationData; }
	 * 
	 * public List<Map<String, Object>> getNationData() { return nationData; }
	 */

	// 올해 해외 토탈 결제 금액
	@GetMapping("/totalPayment")
	public Long getTotalpayment() {
		return iservice.getTotalpayment();
	}

	// 전년 월 대비 올해 월 증감
	@GetMapping("/comparePaymentSamePeriod")
	public Map<String, Object> getComparePaymentSamePeriod(
			@RequestParam(name = "month", required = false) Integer month) {
		// month = 2;
		// System.out.println(">>>>>>>>>>>>>>>>>"+ month);
		return iservice.getComparePaymentSamePeriod(month);
	}

	// 올해 결제 건수가 제일 많은 나라 (순위 리스트업)
	@GetMapping("/highestOrderPayment")
	public List<Map<String, Object>> getHighestOrderPayment() {
		List<Map<String, Object>> result = iservice.getHighestOrderPayment();
		// System.out.println(">>>>>>>>>>>>"+ result.size());

		return result;
	}

	// (차트 데이터) 월별 데이터 추출
//	@GetMapping("/chartDataList")
//	public List<Map<String, Object>> getNationPaymentsDataList(
//			@RequestParam("startMonth") String startMonth
//			, @RequestParam("endMonth") String endMonth) 
//	{
//		log.info("!!!!!!!end"+endMonth);
//		log.info(startMonth);
//		List<Map<String, Object>> result = iservice.getNationPaymentsDataList(startMonth, endMonth);
//		System.out.println(">>>>>>>>>>>>" + result.size());
//
//		return result;
//	}
//	@GetMapping("/chartDataList")
//	public Map<String, Map<String, Map<String, Object>>> getNationPaymentsDataList(
//			@RequestParam("startMonth") String startMonth
//			, @RequestParam("endMonth") String endMonth) 
//	{
//		log.info("!!!!!!!end"+endMonth);
//		log.info(startMonth);
//		Map<String, Map<String, Map<String, Object>>> result = iservice.getNationPaymentsDataList(startMonth, endMonth);
//		System.out.println(">>>>>>>>>>>>" + result.size());
//		
//		return result;
//	}

	@PostMapping("/chartDataList")
	public Map<String, Map<String, Map<String, Object>>> getNationPaymentsDataList(@RequestBody SearchParams params) {

		return iservice.getNationPaymentsDataList(params);
	}

	@Getter
	@Setter
	public static class SearchParams {
		private List<String> genders;
		private List<String> ageGroups;
	    private Map<String, Long> priceRange;
		private List<String> countries;
		private Map<String, String> period;

		public SearchParams() {
			this.genders = new ArrayList<>();
			this.ageGroups = new ArrayList<>();
			this.priceRange = new HashMap<>();
			this.countries = new ArrayList<>();
			this.period = new HashMap<>();
		}

		public List<String> getGenders() {
			if (this.genders == null) {
				this.genders = new ArrayList<>();
			}
			return this.genders;
		}

		public List<String> getAgeGroups() {
			if (this.ageGroups == null) {
				this.ageGroups = new ArrayList<>();
			}
			return this.ageGroups;
		}

		public List<Integer> getAgeGroupsAsNumbers() {
			return convertToNumbers(this.ageGroups);
		}

	    public Map<String, Long> getPriceRange() {
	        if (this.priceRange == null) {
	            this.priceRange = new HashMap<>();
	        }
	        return this.priceRange;
	    }

	    public Long getStartPrice() {
	        return this.priceRange != null ? this.priceRange.get("startPrice") : null;
	    }

	    public Long getEndPrice() {
	        return this.priceRange != null ? this.priceRange.get("endPrice") : null;
	    }

		public List<String> getCountries() {
			if (this.countries == null) {
				this.countries = new ArrayList<>();
			}
			return this.countries;
		}

		public Map<String, String> getPeriod() {
			if (this.period == null) {
				this.period = new HashMap<>();
			}
			return this.period;
		}


		private List<Integer> convertToNumbers(List<String> ageGroups) {
			List<Integer> result = new ArrayList<>();
			for (String ageGroup : ageGroups) {
				String number = ageGroup.replaceAll("\\D+", "");
				result.add(Integer.parseInt(number));
			}
			return result;
		}
	}
}
