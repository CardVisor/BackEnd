package com.project.cardvisor.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardClusterService;

@RestController
@RequestMapping("/CardCluster")
public class CardClusterController {
	
	private final CardClusterService ccserv;
	
	public CardClusterController(CardClusterService ccserv) {
		this.ccserv = ccserv;
	}
	
	@GetMapping("/CardImg")
	public List<String> getCardImgs(){
		return ccserv.getCardImg();
	}
	
	@GetMapping("/MccByCard")
	String getMccByCard() {
		return null;
	}
	
	
	
	

}
