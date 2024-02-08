package com.project.cardvisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.PaymentRepository;

@Service
public class PaymentsService {

	@Autowired
	PaymentRepository payrepo;
	
	public Long TotalAmountPayments() {
		
		Long amount = payrepo.TotalAmountPayments();
		return amount;
	}
public Long LastMonthTotalAmountPayments() {
		
	Long amount1 = payrepo.TotalAmountPayments();
	Long amount = payrepo.LastMonthTotalAmountPayments(); 
	Long totalamount= amount1- amount;
	System.out.println("total:"+totalamount);
		return totalamount;
	}
public Long AbroadTotalAmountPayments() {
	Long amount =payrepo.AbroadTotalAmountPayments();
	return amount;
}

public Long AbroadLastMonthTotalAmountPayments() {
	Long amount = payrepo.AbroadLastMonthTotalAmountPayments();
	Long amount1 = payrepo.AbroadTotalAmountPayments();
	
	return amount1-amount;
}
	
}
