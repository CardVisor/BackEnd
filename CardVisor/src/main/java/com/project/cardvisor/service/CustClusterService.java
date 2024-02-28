
package com.project.cardvisor.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.dto.CustClusterFilterDTO;
import com.project.cardvisor.repo.CardRegInfoRepository;

import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.repo.PaymentRepository;
import com.project.cardvisor.vo.CustomerVO;
import com.project.cardvisor.vo.PaymentsVO;

@Service
public class CustClusterService {
    @Autowired
    CustomerRepository crepo;

	
	@Autowired
	PaymentRepository prepo;
	
	@Autowired
	CardRegInfoRepository crirepo;



    //성별 조회
    public Map<String, Long> getGenderRatio() {
        List<CustomerVO> customers = (List<CustomerVO>) crepo.findAll();
        long maleCount = customers.stream().filter(customer -> customer.getCustGender() == '남').count();
        long femaleCount = customers.stream().filter(customer -> customer.getCustGender() == '여').count();
        Map<String, Long> genderRatio = new HashMap<>();
        genderRatio.put("남성", maleCount);
        
        genderRatio.put("여성", femaleCount);
        return genderRatio;
    }
    
    //성별 최근 6개월간 카드 가입자
    public List<Object[]> getMonthlyRegistrationsByGender() {
        LocalDate now = LocalDate.now();
        LocalDate sixMonthsAgo = now.minusMonths(6).withDayOfMonth(1);
        LocalDate lastMonth = now.withDayOfMonth(1);

        return crirepo.countMonthlyRegistrationsByGender(sixMonthsAgo, lastMonth);
    }
    
    //나이별 조회
    public Map<String, Integer> getAgeGroupCount() {
        Iterable<CustomerVO> customers = crepo.findAll();
        Map<String, Integer> ageGroupCount = new HashMap<>();

        int totalCustomers = 0;


        for (CustomerVO customer : customers) {

            totalCustomers++;
            String ageGroup = calculateAgeGroup(customer.getCustBirth());
            if (ageGroupCount.containsKey(ageGroup)) {
                ageGroupCount.put(ageGroup, ageGroupCount.get(ageGroup) + 1);
            } else {
                ageGroupCount.put(ageGroup, 1);
            }

        }


        ageGroupCount.put("all", totalCustomers);

        return ageGroupCount;
    }
      private String calculateAgeGroup(Date birthDate) {
        //java.sql.Date 인스턴스를 java.util.Date 인스턴스로 변환한 후 toInstant() 메소드를 호출
        LocalDate localBirthDate = new java.util.Date(birthDate.getTime()).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(localBirthDate, LocalDate.now()).getYears();
        // 연령대 계산 로직
        if (age >= 20 && age < 30) {
            return "20대";
        } else if (age >= 30 && age < 40) {
            return "30대";
        } else if (age >= 40 && age < 50) {
            return "40대";
        } else if (age >= 50 && age < 60) {
            return "50대";
        } else if (age >= 60 && age < 70) {
            return "60대";
        } else {
            return "70대 이상";
        }
      }
      //직업별 조회
      public List<Map<String, Object>> findAllJobTypes() {
          return crepo.findAllJobTypes();
      }
      
      //연봉별 조회 
      public List<Object[]> findAllCustSalary() {
          return crepo.findAllCustSalary();
      }
      
      //payment에서 custSalary,payAmount만 출력
      public List<Object[]> getAveragePayAmountBySalary() {

    	    List<PaymentsVO> payments = (List<PaymentsVO>) prepo.findAll();
    	    Map<String, List<Long>> salaryToPayAmounts = new HashMap<>();

    	    for (PaymentsVO payment : payments) {
    	        String custSalary = payment.getRegId().getCustId().getCustSalary();
    	        long payAmount = payment.getPayAmount();

    	        if (!salaryToPayAmounts.containsKey(custSalary)) {
    	            salaryToPayAmounts.put(custSalary, new ArrayList<>());
    	        }
    	        salaryToPayAmounts.get(custSalary).add(payAmount);
    	    }

    	    List<Object[]> salaryToAveragePayAmount = new ArrayList<>();
    	    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA); // 통화 형식을 설정하는 포맷터

    	    for (Map.Entry<String, List<Long>> entry : salaryToPayAmounts.entrySet()) {
    	        String salary = entry.getKey();
    	        List<Long> payAmounts = entry.getValue();

    	        double totalPayAmount = 0;
    	        for (Long payAmount : payAmounts) {
    	            totalPayAmount += payAmount;
    	        }
    	        double averagePayAmount = totalPayAmount / payAmounts.size();
    	        // 평균 금액을 한국 원(₩) 단위로 변환
    	        String averagePayAmountInKRW = formatter.format(averagePayAmount);
    	        salaryToAveragePayAmount.add(new Object[]{salary, averagePayAmountInKRW});
    	    }

    	    return salaryToAveragePayAmount;
    	}


    public int CustomerTotalCount() {
        List<CustomerVO> customers = (List<CustomerVO>) crepo.findAll();
        int CustomerCount = customers.size();
        return CustomerCount;
    }
    
    // 고객 수 조회
    public Map<String, Long> getCustomerStats() {
        Map<String, Long> gstats = new HashMap<>();
        gstats.put("total", crepo.getTotalCustomers());
        gstats.put("male", crepo.getMaleCustomers());
        gstats.put("female", crepo.getFemaleCustomers());
        return gstats;
    }
    // 고객 평균 연령 조회
    public Map<String, Double> getCustAverages() {
        Map<String, Double> astats = new HashMap<>();
        List<Object[]> averages = crepo.getAverageAge();

        for (Object[] average : averages) {
            String gender = (String) average[0];
            Double age = (Double) average[1];
            Double cAge = Math.floor(age * 10) / 10.0; // 소수점 첫째 자리에서 잘라냄

            if (gender.equals("all")) {
                astats.put("totalAverageAge", cAge);
            } else {
                astats.put(gender + "AverageAge", cAge);
            }
        }

        return astats;
    }
    
    //가장 많은 수의 고객을 가진 연봉 카테고리
    public Map<String, String> getMostCommonSalaries() {
        Map<String, String> results = new HashMap<>();
        List<Object[]> mostCommonSalaries = crepo.getMostCommonSalary();

        for (Object[] row : mostCommonSalaries) {
            String group = (String) row[0];
            String salary = (String) row[1];
            results.put(group, salary);
        }

        return results;
    }
    
    //성별에 따른 평균 소비금액
    public Map<String, String> getAveragePaymentAmountInKRW() {
        Map<String, String> results = new HashMap<>();
        List<Object[]> averagePaymentAmounts = prepo.getAveragePaymentAmount();
        double totalAverage = 0.0;
        int count = 0;
        
        //원화 포맷으로 변환
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);

        for (Object[] row : averagePaymentAmounts) {
            Character groupChar = (Character) row[0];  // Character로 캐스팅
            String group = groupChar.toString();  // Character를 String으로 변환
            double averagePaymentAmount = (Double) row[1];
            results.put(group, formatter.format(averagePaymentAmount));
            totalAverage += averagePaymentAmount;
            count++;
        }

        if (count > 0) {
            results.put("all", formatter.format(totalAverage / count));
        }

        return results;
    }
    
    //성별 사용카드 상위 3개 이름
    public Map<String, List<String>> getTop3CardTypesByGenderAndAll() {
        List<Object[]> resultsByGender = crirepo.findTop3CardTypesByGender();
        List<String> resultsForAll = crirepo.findTop3CardTypes();

        Map<String, List<String>> top3CardTypes = new HashMap<>();

        for (Object[] result : resultsByGender) {
            String gender = String.valueOf(result[0]);
            String cardName = (String) result[1];

            if (!top3CardTypes.containsKey(gender)) {
                top3CardTypes.put(gender, new ArrayList<>());
            }

            top3CardTypes.get(gender).add(cardName);
        }

        top3CardTypes.put("all", resultsForAll);

        return top3CardTypes;
    }
    //mcc 상위 3개
    public Map<String, List<String>> getTop3MccCodeByGender() {
        List<Object[]> results = prepo.getTop3MccCodeByGender();
        Map<String, List<String>> top3MccCodeByGender = new HashMap<>();

        for (Object[] result : results) {
            String gender = (String) result[0];
            String mccName = (String) result[1];

            if (!top3MccCodeByGender.containsKey(gender)) {
                top3MccCodeByGender.put(gender, new ArrayList<>());
            }

            top3MccCodeByGender.get(gender).add(mccName);
        }

        return top3MccCodeByGender;
    }
    
    public Map<String, Float> getAverageAgeGroups() {
        Map<String, BigDecimal> ageGroups = crepo.findAgeGroups();
        Map<String, BigDecimal> ageSums = crepo.findAgeSums();
        Map<String, Float> averageAgeGroups = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.0");
        BigDecimal totalAgeAll = BigDecimal.ZERO;
        BigDecimal totalCountAll = BigDecimal.ZERO;

        for (String ageGroup : ageGroups.keySet()) {
            BigDecimal totalAge = ageSums.get(ageGroup);
            BigDecimal count = ageGroups.get(ageGroup);
            Float average = totalAge.floatValue() / count.floatValue();
            averageAgeGroups.put(ageGroup, average);
            totalAgeAll = totalAgeAll.add(totalAge);
            totalCountAll = totalCountAll.add(count);
        }

        Float averageAll = Float.valueOf(df.format(totalAgeAll.floatValue() / totalCountAll.floatValue()));
        averageAgeGroups.put("all", averageAll);

        // 모든 값에 대해 소수 첫 번째 자리까지만 표시
        for (String key : averageAgeGroups.keySet()) {
            Float value = averageAgeGroups.get(key);
            averageAgeGroups.put(key, Float.valueOf(df.format(value)));
        }
        
        return averageAgeGroups;
       
    }
    
    public List<List<Object>> getTopSalaryByAgeRange() {
        List<Object[]> results = crepo.getTopSalaryByAgeRange();

        List<List<Object>> topSalaryByAgeRange = new ArrayList<>();

        for (Object[] result : results) {
            List<Object> row = new ArrayList<>();
            row.add(result[0]); // age_range
            row.add(result[1]); // salary

            topSalaryByAgeRange.add(row);
        }

        return topSalaryByAgeRange;
 
    }
    
    public List<Map<String, Object>> findAveragePaymentByAgeRange() {
        List<Map<String, Object>> rawData = prepo.findAveragePaymentByAgeRange();
        List<Map<String, Object>> result = new ArrayList<>();
        
        // 한국 로케일을 사용하여 통화 형식 지정
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        
        for (Map<String, Object> data : rawData) {
            Map<String, Object> formattedData = new HashMap<>();
            formattedData.put("age_range", data.get("age_range"));
            
            // pay_amount 값을 통화 형식으로 변환
            double payAmount = ((Number) data.get("AVG(pay_amount)")).doubleValue();
            String formattedPayAmount = formatter.format(payAmount);
            
            formattedData.put("pay_amount", formattedPayAmount);
            result.add(formattedData);
        }

        return result;
    }
    
    public List<Map<String, String>> getTop3CardTypesByAgeRange() {
        List<Object[]> rawData = crirepo.findTop3CardTypesByAgeRange();
        List<Map<String, String>> result = new ArrayList<>();

        for (Object[] row : rawData) {
            Map<String, String> map = new HashMap<>();
            map.put("ageRange", (String) row[0]);
            map.put("cardName", (String) row[1]);
            result.add(map);
        }

        return result;
    }
    
    
    public List<Map<String, String>> getTop3CardTypes() {
        List<Map<String, String>> allCardTypes = findTop3CardTypes();
        List<Map<String, String>> cardTypesByAgeRange = getTop3CardTypesByAgeRange();

        List<Map<String, String>> allResults = new ArrayList<>();
        allResults.addAll(allCardTypes);
        allResults.addAll(cardTypesByAgeRange);

        return allResults;
    }

    private List<Map<String, String>> findTop3CardTypes() {
        List<String> rawData = crirepo.findTop3CardTypes();
        List<Map<String, String>> result = new ArrayList<>();

        for (String cardName : rawData) {
            Map<String, String> map = new HashMap<>();
            map.put("ageRange", "all");
            map.put("cardName", cardName);
            result.add(map);
        }

        return result;
    }
    
    public List<Object[]> findTopMccCodes() {
        return prepo.findTopMccCodes();
    }
    
    // 직종별 고객 인원수와 전체 고객 인원수
    public List<Object[]> countByJobTypeAndAll() {
    	return crepo.countByJobTypeAndAll();
    }
    
    // 직종별 평균 연령과 전체 평균 연령
    public List<Object[]> getAverageAgeByJobTypeAndAll() {
        List<Object[]> rawResults = crepo.getAverageAgeByJobTypeAndAll();

        return rawResults.stream()
                .map(result -> new Object[] {result[0], new BigDecimal((double)result[1]).setScale(1, RoundingMode.FLOOR)})
                .collect(Collectors.toList());
    }
    
    //직종별 top연봉 카테고리
    public List<Object[]> MostCommonByJobTypeAndAll() {
    	return crepo.MostCommonByJobTypeAndAll();
    }
    
    //연봉별 고객 수 조회
	public List<Object[]> findAllCustSalaryAndAll() {
		return crepo.findAllCustSalaryAndAll();
	}
	
	//연봉 별 고객 평균 연령
	public List<Object[]> getAgeSalaryRangeAndAll() {
        List<Object[]> rawsResults = crepo.getAgeSalaryRangeAndAll();

        return rawsResults.stream()
                .map(result -> new Object[] {result[0], new BigDecimal((double)result[1]).setScale(1, RoundingMode.FLOOR)})
                .collect(Collectors.toList());
	}
	
	//직업별 평균 소비금액
	public List<Map<String, Object>> AveragePaymentByJobTypeAndAll() {
	    List<Object[]> rawData = prepo.AveragePaymentByJobTypeAndAll();
	    List<Map<String, Object>> result = new ArrayList<>();
	    
	    // 한국 로케일을 사용하여 통화 형식 지정
	    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
	    
	    for (Object[] data : rawData) {
	        Map<String, Object> formattedData = new HashMap<>();
	        formattedData.put("job_type", data[0]);
	        
	        // pay_amount 값을 통화 형식으로 변환
	        double payAmount = ((Number) data[1]).doubleValue();
	        String formattedPayAmount = formatter.format(payAmount);
	        
	        formattedData.put("avg_payment", formattedPayAmount);
	        result.add(formattedData);
	    }

	    return result;
	}
	
	//직업별 사용카드 상위 3개
	public Map<String, Object> getTopCardData() {
        List<Object[]> topCardsByJobType = crirepo.findTop3CardTypesByJobType();
        List<String> topCards = crirepo.findTop3CardTypes();

        Map<String, Object> result = new HashMap<>();
        result.put("topCardsByJobType", topCardsByJobType);
        result.put("all", topCards);

        return result;
    }
	
	//직업별 주 사용처 상위 3개
	public List<Object[]> getTop3MccCodeByJobType() {
		return prepo.getTop3MccCodeByJobType();
	}
	
	//연봉별 조회
	public List<Object[]> findCustSalaryData(){
		return crepo.findCustSalaryData();
		
	}
	
	// 연봉별 평균 소비금액
	public List<Map<String, Object>> paymentBySalaryRangeAndAll() {
		List<Object[]> rawData = prepo.paymentBySalaryRangeAndAll();

		List<Map<String, Object>> result = new ArrayList<>();

	    // 한국 로케일을 사용하여 통화 형식 지정
	    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);

	    for (Object[] data : rawData) {
	        Map<String, Object> formattedData = new HashMap<>();
	        formattedData.put("cust_salary", data[0]);

	        // pay_amount 값을 통화 형식으로 변환
	        double payAmount = ((Number) data[1]).doubleValue();
	        String formattedPayAmount = formatter.format(payAmount);

	        formattedData.put("avg_payment", formattedPayAmount);
	        result.add(formattedData);
	    }

	    return result;
	}
	
//	public List<Map<String, Object>> paymentBySalaryRangeAndAll() {
//	    List<Object[]> rawData = prepo.paymentBySalaryRangeAndAll();
//
//	    List<Map<String, Object>> result = new ArrayList<>();
//
//	    for (Object[] data : rawData) {
//	        Map<String, Object> formattedData = new HashMap<>();
//	        formattedData.put("cust_salary", data[0]);
//	        formattedData.put("avg_payment", data[1]); // 통화 형식으로 변환하지 않고 원래 데이터 그대로 사용
//	        result.add(formattedData);
//	    }
//
//	    return result;
//	}
	
	//연봉별 사용카드 상위 3개
	public Map<String, List> getCardData() {
        List<String> top3CardTypes = crirepo.findTop3CardTypes();
        List<Object[]> top3CardTypesByCustSalary = crirepo.findTop3CardTypesByCustSalary();

        Map<String, List> result = new HashMap<>();
        result.put("all", top3CardTypes);
        result.put("top3CardTypes", top3CardTypesByCustSalary);

        return result;
    }
	
	public List<Object[]> getTop3MccCodeByCustSalary() {
		return prepo.getTop3MccCodeByCustSalary();
	}
	
	//filter
	//고객 수
	public Long getCustomerCount(CustClusterFilterDTO filter) {
        return crepo.countDistinctByFilters(filter.getGender(), filter.getAgeRange(),  filter.getJobType(), filter.getSalaryRange());
    }
	//평균연령
	public Double getCustomerAge(CustClusterFilterDTO filter) {
		 Double averageAge = crepo.averageAgeByFilters(filter.getGender(), filter.getAgeRange(),  filter.getJobType(), filter.getSalaryRange());
		 return Math.round(averageAge * 10.0) / 10.0;
	}
	//평균연봉
	public List<String> getCustomerSalary(CustClusterFilterDTO filter) {
		return crepo.findDistinctSalaryByFilters(filter.getGender(), filter.getAgeRange(),  filter.getJobType(), filter.getSalaryRange());
	}
	//소비금액
	public Double getCustomerPayment(CustClusterFilterDTO filter) {
		return prepo.findAveragePaymentByFilters(filter.getGender(), filter.getAgeRange(),  filter.getJobType(), filter.getSalaryRange());		
	}
	//사용카드
	public List<Object[]> getCustomerCardName(CustClusterFilterDTO filter) {
		return crirepo.findTop5CardByFilters(filter.getGender(), filter.getAgeRange(), filter.getJobType(), filter.getSalaryRange());
	}
	//사용처
	public List<Object[]> getCustomerMcc(CustClusterFilterDTO filter) {
		return prepo.findTop5CategoryByFilters(filter.getGender(), filter.getAgeRange(),  filter.getJobType(), filter.getSalaryRange());
	}

}


