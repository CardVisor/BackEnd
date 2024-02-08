package com.project.cardvisor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardReginfoService;
import com.project.cardvisor.service.CustClusterService;

@RestController
@RequestMapping("/customer")
public class CustClusterController {
	
@Autowired
 CustClusterService genderRatioService;

@Autowired
CardReginfoService cardreginfoservice;
   

    @GetMapping("/genderRatio")
    public Map<String, Long> getGenderRatio() {
        return genderRatioService.getGenderRatio();
    }
 
    
}

