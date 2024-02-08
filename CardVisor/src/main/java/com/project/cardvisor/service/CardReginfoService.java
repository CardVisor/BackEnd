package com.project.cardvisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.CardRegInfoRepository;

@Service
public class CardReginfoService {
	@Autowired
	CardRegInfoRepository crirepo;
	
	//매월 1일에 증가된 회원수
	public int addOnedaycustomer() {
		int count = crirepo.addOnedaycustomer();
		return count;
	}

}
