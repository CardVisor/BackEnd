package com.project.cardvisor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardClusterService;
import com.project.cardvisor.vo.CardListVO;

@RestController
@RequestMapping("/CardCluster")
public class CardClusterController {
	
	private final CardClusterService ccserv;
	
	public CardClusterController(CardClusterService ccserv) {
		this.ccserv = ccserv;
	}
	
	@GetMapping("/MccByCard")
	String getMccByCard() {
		return null;
	}
	
	@GetMapping("/Cards")
	public ResponseEntity<List<CardListVO>> getCards(@RequestParam("month") String month, @RequestParam("sort") String sort){
		// 필터요소 정상 출력 확인 
		//System.out.println(month);
		//System.out.println(sort);
		return ccserv.getCardsBySort(month, sort);

	}
	
	@GetMapping("/CardDetails")
	public Map<Object,Object> getCardDetails(@RequestParam("month") String month, @RequestParam("type") String type){
		return ccserv.getCardsDetail(month, type);
	}
	
	@GetMapping("/MccCharts")
	public List<Map<String, Object>> getCardMccChart(@RequestParam("month") String month, @RequestParam("type") String type){
		return ccserv.getMccChart(month, type);
	}
	
	@GetMapping("/MccChartsAll")
	public Map<String, Object> getCardMccAllChart(@RequestParam("month") String month, @RequestParam("type") String type){
		return ccserv.getMccAllChart(month, type);
	}
	
	
	
	

}
