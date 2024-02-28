package com.project.cardvisor.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.BenefitClusterService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/benefitCluster")
@RestController
@RequiredArgsConstructor
public class BenefitClusterController {
	
	private final BenefitClusterService bser;
	
	@PostMapping("/benefitTreeChart")
	public LinkedList<Object> benefitTreeChartByMCC(@RequestBody Map<String, Object> data){
		String dateStr = (String)data.get("date");
		return bser.benefitTreeMapByMCC(dateStr);
	}
	
	@PostMapping("/benefitTopAndBottom")
	public Map<String,Object> benefitTop5ByMCC(@RequestBody Map<String, Object> data){
		String selectOption = (String)data.get("selectOption");
		String dateStr = (String)data.get("date");
		return bser.benefitTopAndBottomByMCC(selectOption, dateStr);
	}
	
	@PostMapping("/benefitDetail")
	public List<Map<String, Object>> BenefitDetail(@RequestBody Map<String, String> data){
		return bser.benefitDetailByCategory((String)data.get("cateName"), (String)data.get("date"), (String)data.get("selectOption"));
	}
	
	@PostMapping("/benefitRecommend")
	public Map<String, List<Map<String, Object>>> BenefitRecommend(@RequestBody Map<String, Object> data){
		return bser.benefitRecommendForValueEstimation(data);
	}
	
	@PostMapping("/benefitCombination")
	public List<Map<String, Object>> benefitCombinationResult(@RequestBody Map<String, Object> data){
		return bser.benefitCombinationResult(data);
	}
	
}
