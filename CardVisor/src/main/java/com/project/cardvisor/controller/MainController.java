package com.project.cardvisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardReginfoService;
import com.project.cardvisor.service.CustClusterService;
import com.project.cardvisor.service.PaymentsService;

@RestController
@RequestMapping("/main")
public class MainController {
	@Autowired
	 CustClusterService genderRatioService;

	@Autowired
	CardReginfoService cardreginfoservice;
	@Autowired
	PaymentsService paymentservice;
	
	
	
	
	   //총 회원 수
    @GetMapping("/totalCustomer")
    public int CustomerTotalCount () {
    	int size = genderRatioService.CustomerTotalCount();
    	System.out.println("size:"+size);
    	return size;
    	}
	//매월 1일에 증가된 회원수
    @GetMapping("/addOneDayCustomer")
    public int addOnedaycustomer () {
    	int size = cardreginfoservice.addOnedaycustomer();
    	System.out.println("size:"+size);
    	return size;
    	}
    
    @GetMapping("/totalAmount")
    public Long TotalAmountPayments() {
    	Long amount2 = paymentservice.TotalAmountPayments(); 
        System.out.println("amount2"+amount2);
    	return amount2;
    }
    
    @GetMapping("/lastMonthTotalAmount")
    public Long LastMonthTotalAmountPayments() {
     	Long totalamount = paymentservice.LastMonthTotalAmountPayments();
    System.out.println("totalamount"+totalamount);
    	return totalamount;
    }
    
    @GetMapping("/abroadTotalAmount")
    public Long AbroadTotalAmountPayments() {
    	Long amount2 = paymentservice.AbroadTotalAmountPayments(); 
        System.out.println("Abroadamount2"+amount2);
    	return amount2;
    }
    
    @GetMapping("/abroadLastMonthTotalAmount")
    public Long AbroadLastMonthTotalAmountPayments() {
     	Long totalamount = paymentservice.AbroadLastMonthTotalAmountPayments();
    System.out.println("Abroadtotalamount"+totalamount);
    	return totalamount;
    }
}
