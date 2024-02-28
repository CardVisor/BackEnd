package com.project.cardvisor.controller;

import java.io.Console;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.dto.CustClusterFilterDTO;
import com.project.cardvisor.dto.CustClusterStatsDTO;
import com.project.cardvisor.service.CardReginfoService;

import com.project.cardvisor.service.CustClusterService;
import com.project.cardvisor.vo.CustomerVO;

@RestController
@RequestMapping("/customer")
public class CustClusterController {

	@Autowired
	CustClusterService cService;

	@Autowired
	CardReginfoService cardreginfoservice;

   
	//차트
    @GetMapping("/genderRatio")
    public Map<String, Long> getGenderRatio() {
        return cService.getGenderRatio();
    }
    
    @GetMapping("/monthlyRegistrationsByGender")
    public ResponseEntity<List<Object[]>> getMonthlyRegistrationsByGender() {
        List<Object[]> monthlyRegistrationsByGender = cService.getMonthlyRegistrationsByGender();
        return new ResponseEntity<>(monthlyRegistrationsByGender, HttpStatus.OK);
    }
    
    @GetMapping("/ageGroup")
    public ResponseEntity<Map<String, Integer>> getAgeGroupCount() {
      Map<String, Integer> ageGroupCount = cService.getAgeGroupCount();
      return new ResponseEntity<>(ageGroupCount, HttpStatus.OK);
    }
    
    @GetMapping("/jobTypes")
    public List<Map<String, Object>> getAllJobTypes() {
        return cService.findAllJobTypes();
    }
    
    @GetMapping("/custSalary")
    public List<Object[]> getAllCustSalary() {
        return cService.findAllCustSalary();
    }
    
    @GetMapping("/amountBySalary")
    public ResponseEntity<List<Object[]>> getAveragePayAmountBySalary() {
        List<Object[]> averagePayAmountBySalary = cService.getAveragePayAmountBySalary();
        return ResponseEntity.ok(averagePayAmountBySalary);
    }
    
    //데이터 
    @GetMapping("/genderInfo")
    public Map<String, Object> getAllCustomerInfo() {
        Map<String, Object> genderInfo = new HashMap<>();

        genderInfo.put("customerStats", cService.getCustomerStats());
        genderInfo.put("custAverages", cService.getCustAverages());
        genderInfo.put("mostCommonSalaries", cService.getMostCommonSalaries());
        genderInfo.put("averagePaymentAmountInKRW", cService.getAveragePaymentAmountInKRW());
        genderInfo.put("top3CardTypesByGender", cService.getTop3CardTypesByGenderAndAll());
        genderInfo.put("top3MccCodeByGender", cService.getTop3MccCodeByGender());

        return genderInfo;
    }
    
    @GetMapping("/averageAgeGroups")
    public ResponseEntity<Map<String, Float>> getAverageAgeGroups() {
        Map<String, Float> averageAgeGroups = cService.getAverageAgeGroups();
        return ResponseEntity.ok(averageAgeGroups);
    }
    
    @GetMapping("/topSalaryByAgeRange")
    public ResponseEntity<List<List<Object>>> getTopSalaryByAgeRange() {
        List<List<Object>> result = cService.getTopSalaryByAgeRange();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/averageByAgeRange")
    public List<Map<String, Object>> getAveragePaymentsByAgeRange() {
        return cService.findAveragePaymentByAgeRange();
    }
    
    @GetMapping("/alltop3CardTypes")
    public ResponseEntity<List<Map<String, String>>> getTop3CardTypes() {
        List<Map<String, String>> result = cService.getTop3CardTypes();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/top3MccCodeByAgeRange")
    public ResponseEntity<List<Object[]>> getTopMccCodes() {
        List<Object[]> mccCodes = cService.findTopMccCodes();
        return new ResponseEntity<>(mccCodes, HttpStatus.OK);
    }

    @GetMapping("/countByJobTypeAndAll")
    public List<Object[]> countByJobTypeAndAll() {
    	return cService.countByJobTypeAndAll();
    }
    
    @GetMapping("/getAverageAgeByJobTypeAndAll")
    public List<Object[]> getAverageAgeByJobTypeAndAll() {
    	return cService.getAverageAgeByJobTypeAndAll();
    }
    
    @GetMapping("/mostsalaryData")
    public List<Object[]> MostCommonByJobTypeAndAll() {
    	return cService.MostCommonByJobTypeAndAll();
    }
    
    @GetMapping("custsalaryNum")
    public List<Object[]> findAllCustSalaryAndAll() {
    	return cService.findAllCustSalaryAndAll();
    }
    
    @GetMapping("ageSalaryRangeAndAll")
    public List<Object[]> getAgeSalaryRangeAndAll() {
    	return cService.getAgeSalaryRangeAndAll();
    }
    
    @GetMapping("paymentByJobTypeAndAll")
    public List<Map<String, Object>> getAveragePaymentByJobTypeAndAll() {
        return cService.AveragePaymentByJobTypeAndAll();
    }
    
    @GetMapping("topCardsByJobType")
    public Map<String, Object> getTopCardData() {
        return cService.getTopCardData();
    }
    
    @GetMapping("findTop3CardTypesByJobType")
    public List<Object[]> getTop3MccCodeByJobType() {
    	return cService.getTop3MccCodeByJobType();
    }
    
    @GetMapping("findCustSalaryData")
    public List<Object[]> findCustSalaryData(){
    	return cService.findCustSalaryData();
    }
    
    @GetMapping("paymentBySalaryAndAll")
    public List<Map<String, Object>> paymentBySalaryRangeAndAll() {
    	return cService.paymentBySalaryRangeAndAll();
    }
    
    @GetMapping("top3CardByCustSalary")
    public Map<String, List> getCardData() {
    	return cService.getCardData();
    }
    
    @GetMapping("top3MccCodeByCustSalary")
    public List<Object[]> getTop3MccCodeByCustSalary() {
    	return cService.getTop3MccCodeByCustSalary();
    }
   
    
    
    //filter
    @PostMapping("/filterstats")
    public CustClusterStatsDTO  getCustomerStats(@RequestBody Map<String,Object> filter) {
    	
        List<String> gender = (List<String>) filter.get("gender");
        List<Integer> ageRange = (List<Integer>) filter.get("ageRange");
        String jobType = (String) filter.get("jobType");
        String salaryRange = (String) filter.get("salaryRange");

        CustClusterFilterDTO filterDTO = new CustClusterFilterDTO(gender, ageRange, jobType, salaryRange);

        CustClusterStatsDTO stats = new CustClusterStatsDTO();
        stats.setCount(cService.getCustomerCount(filterDTO));
        stats.setAverageAge(cService.getCustomerAge(filterDTO));
        stats.setDistinctSalaries(cService.getCustomerSalary(filterDTO));
        stats.setAveragePayment(cService.getCustomerPayment(filterDTO));
        stats.setCardName(cService.getCustomerCardName(filterDTO));
        stats.setCardMcc(cService.getCustomerMcc(filterDTO));

        return stats;

    }
    
}
