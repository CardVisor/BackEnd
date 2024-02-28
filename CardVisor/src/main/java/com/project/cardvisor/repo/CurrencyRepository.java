package com.project.cardvisor.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.CurrencyVO;

public interface CurrencyRepository extends CrudRepository<CurrencyVO, Integer>{

	@Query(value = "select * from currency c where c.currency_date = :currency_date",nativeQuery = true)
	List<CurrencyVO> findByCurrency_date(@Param("currency_date") Timestamp currency_date);
	
	@Query(value = "SELECT * "
			+ "FROM currency c "
			+ "WHERE c.currency_date = ( "
			+ "    SELECT MAX(currency_date) "
			+ "    FROM currency "
			+ "    WHERE currency_date <= CURDATE() "
			+ ")", nativeQuery = true)
	List<CurrencyVO> findByLatestCurrencyData();
}
