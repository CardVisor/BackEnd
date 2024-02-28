package com.project.cardvisor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.cardvisor.vo.CardRegInfoVO;
import com.project.cardvisor.vo.CustomerVO;

public interface CardRegRepository extends CrudRepository<CardRegInfoVO, String>{
	
	@Query(value="select card from CardRegInfoVO card where card.custId = ?1")
	List<CardRegInfoVO> selectByCustInfo(CustomerVO vo);
}
