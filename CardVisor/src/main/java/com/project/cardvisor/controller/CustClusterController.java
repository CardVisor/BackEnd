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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardReginfoService;

import com.project.cardvisor.service.CustClusterService;

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


	

}
