package com.project.cardvisor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.cardvisor.vo.CardRegInfoVO;

public interface CardRegInfoRepository extends CrudRepository<CardRegInfoVO, String>, JpaRepository<CardRegInfoVO, String>{
	
	@Query(value="SELECT COUNT(DISTINCT cri.cust_id) "
			+ "FROM card_reg_info cri "
			+ "WHERE cri.reg_date > DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), INTERVAL 1 DAY) "
			+ "AND cri.cust_id NOT IN ( "
			+ "SELECT cust_id FROM card_reg_info "
			+ "WHERE reg_date < DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), INTERVAL 1 DAY))",nativeQuery = true)
	int addOnedaycustomer();
	
}
