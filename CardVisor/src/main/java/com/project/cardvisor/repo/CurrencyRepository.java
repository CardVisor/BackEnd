package com.project.cardvisor.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.cardvisor.vo.CurrencyVO;



public interface CurrencyRepository extends CrudRepository<CurrencyVO, Integer>{
	//List<CurrencyVO> findByCurrency_date(Timestamp currency_date);
}
