package com.project.cardvisor.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.project.cardvisor.vo.CardListVO;

import lombok.Value;

public interface CardListRepository extends CrudRepository<CardListVO, Integer>,JpaRepository<CardListVO, Integer> {

	// bohyeon start

	// 이용률 높은 순
	@Query(value = "SELECT cl.*, coalesce(ri.card_count, 0) as usage_count " + "FROM card_list cl "
			+ "LEFT JOIN (SELECT card_type, COUNT(*) as card_count FROM card_reg_info WHERE expire_date > ?1 GROUP BY card_type) ri ON cl.card_type = ri.card_type "
			+ "ORDER BY usage_count DESC", nativeQuery = true)
	List<CardListVO> getCardsBySortAsc(LocalDate date2);

	// 이용률 낮은 순
	@Query(value = "SELECT cl.*, coalesce(ri.card_count, 0) as usage_count " + "FROM card_list cl "
			+ "LEFT JOIN (SELECT card_type, COUNT(*) as card_count FROM card_reg_info WHERE expire_date > ?1 GROUP BY card_type) ri ON cl.card_type = ri.card_type "
			+ "ORDER BY usage_count ASC", nativeQuery = true)
	List<CardListVO> getCardsBySortDesc(LocalDate date2);

	// 고객 토탈 수
	@Query(value = "select count(*) as card_count " + "from card_reg_info "
			+ "where card_type = ?1 and expire_date > ?2", nativeQuery = true)
	int getCustNum(Integer type, LocalDate dateFlag);

	// 고객 주 사용 연령층
	@Query(value = "SELECT age_group\n"
			+ "FROM (\n"
			+ "    SELECT CASE \n"
			+ "        WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) BETWEEN 20 AND 29 THEN '20대'\n"
			+ "        WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) BETWEEN 30 AND 39 THEN '30대'\n"
			+ "        WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) BETWEEN 40 AND 49 THEN '40대'\n"
			+ "    WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) BETWEEN 50 AND 59 THEN '50대'\n"
			+ "    WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) BETWEEN 60 AND 69 THEN '60대'\n"
			+ "    WHEN FLOOR(DATEDIFF(CURRENT_DATE, c.cust_birth) / 365) > 69 THEN '70대 이상'\n"
			+ "        END AS age_group,\n"
			+ "    COUNT(*) as count\n"
			+ "    FROM card_reg_info cr\n"
			+ "    JOIN customer c ON cr.cust_id = c.cust_id\n"
			+ "    WHERE cr.card_type = ?1\n"
			+ "    and cr.expire_date  > ?2 "
			+ "    GROUP BY age_group\n"
			+ "    ORDER BY count DESC\n"
			+ "    LIMIT 1\n"
			+ ") AS age_counts;", nativeQuery = true)
	String getMajorAge(int typeNum, LocalDate dateFlag);
	
	
	

	@Query(value ="SELECT \n"
			+ "  c.cust_gender, \n"
			+ "  round(COUNT(*) * 100.0 / sum(count(*)) over(),2) as percentage\n"
			+ "FROM card_reg_info cr\n"
			+ "JOIN customer c ON cr.cust_id = c.cust_id\n"
			+ "WHERE cr.card_type = ?1 and cr.expire_date  > ?2\n"
			+ "GROUP BY c.cust_gender;", nativeQuery = true)
	List<Map<Character, Object>> getGenderPercentage(int typeNum, LocalDate dateFlag);
	
	
	@Query(value = "SELECT \n"
			+ "count(*) \n"
			+ "FROM card_reg_info cr\n"
			+ "JOIN customer c ON cr.cust_id = c.cust_id\n"
			+ "WHERE cr.card_type = ?1 and cr.expire_date  > ?2\n and c.cust_gender='남'", nativeQuery=true)
	Long getMaleCnt(int typeNum, LocalDate dateFlag);
	
	@Query(value = "SELECT \n"
			+ "count(*) \n"
			+ "FROM card_reg_info cr\n"
			+ "JOIN customer c ON cr.cust_id = c.cust_id\n"
			+ "WHERE cr.card_type = ?1 and cr.expire_date  > ?2\n and c.cust_gender='여'", nativeQuery=true)
	Long getFemaleCnt(int typeNum, LocalDate dateFlag);
	
	
	@Query(value="select benefit_detail\n"
			+ "from benefit\n"
			+ "where benefit_id in (select benefit_id\n"
			+ "from card_benefit \n"
			+ "where card_type=?1);",nativeQuery=true)
	List<String> getBenefitList(int typeNum);
	
	
	@Query(value="SELECT SUM(p.pay_amount) as total, m.ctg_name\n"
			+ "FROM payments p\n"
			+ "JOIN card_reg_info c ON p.reg_id = c.reg_id\n"
			+ "JOIN mcc m ON p.mcc_code = m.mcc_code\n"
			+ "WHERE c.card_type = ?1 \n"
			+ "AND p.pay_date BETWEEN ?2 AND ?3 \n"
			+ "GROUP BY p.mcc_code, m.ctg_name\n"
			+ "ORDER BY total DESC\n"
			+ "LIMIT 6;", nativeQuery=true)
	List<Map<String, Object>> getMccTopList(int typeNum, LocalDate dateFlag, LocalDate endDate);
	
	
	@Query(value="SELECT SUM(p.pay_amount) as total, m.ctg_name\n"
			+ "FROM payments p\n"
			+ "JOIN card_reg_info c ON p.reg_id = c.reg_id\n"
			+ "JOIN mcc m ON p.mcc_code = m.mcc_code\n"
			+ "WHERE c.card_type = ?1 \n"
			+ "AND p.pay_date BETWEEN ?2 AND ?3 \n"
			+ "GROUP BY p.mcc_code, m.ctg_name;",nativeQuery=true)
	List<Map<String, Object>> getMccAllList(int typeNum, LocalDate dateFlag, LocalDate endDate);



	// bohyeon end

	@Query(value="SELECT cl.card_annual_fee, cl.card_name, ci.col "
			+ "FROM card_list cl "
			+ "JOIN ( "
			+ "    SELECT card_type, COUNT(*) AS col "
			+ "    FROM card_reg_info "
			+ "    WHERE expire_date > NOW()"
			+ "    GROUP BY card_type "
			+ ") ci ON cl.card_type = ci.card_type "
			+ "ORDER BY ci.col DESC "
			+ "LIMIT 5", nativeQuery = true)
	List<Map<String, Object>> selectTop5CardList();


	

	

}
