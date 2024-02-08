package com.project.cardvisor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.CardListRepository;

@Service
public class CardClusterService {
	
	private final CardListRepository clrepo;
	
	public CardClusterService(CardListRepository clrepo) {
		this.clrepo = clrepo;
	}
	
	public List<String> getCardImg(){
		
		return null;
	}
	
	
}
