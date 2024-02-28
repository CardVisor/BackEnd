package com.project.cardvisor.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.CardRegInfoVO;

public interface CardRegInfoRepository extends CrudRepository<CardRegInfoVO, String>, JpaRepository<CardRegInfoVO, String>{
	
	@Query(value="SELECT COUNT(DISTINCT cri.cust_id) "
			+ "FROM card_reg_info cri "
			+ "WHERE cri.reg_date > DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), INTERVAL 1 DAY) "
			+ "AND cri.cust_id NOT IN ( "
			+ "SELECT cust_id FROM card_reg_info "
			+ "WHERE reg_date < DATE_ADD(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 MONTH)), INTERVAL 1 DAY))",nativeQuery = true)
	int addOnedaycustomer();
	

	
	////지현
	//성별(남,녀) 사용카드 상위 3개
	@Query(value = "SELECT subquery2.cust_gender, t.card_name " + "FROM card_list t " + "JOIN ("
			+ "SELECT subquery.cust_gender, subquery.card_type " + "FROM ("
			+ "SELECT c.cust_gender, r.card_type, ROW_NUMBER() OVER(PARTITION BY c.cust_gender ORDER BY COUNT(*) DESC) as rn "
			+ "FROM customer c " + "JOIN card_reg_info r ON r.cust_id = c.cust_id "
			+ "GROUP BY c.cust_gender, r.card_type) as subquery " + "WHERE rn <= 3) as subquery2 "
			+ "ON t.card_type = subquery2.card_type", nativeQuery = true)
	List<Object[]> findTop3CardTypesByGender();
	
	// 전체고객(all) 사용카드 상위 3개
	@Query(value = "SELECT t.card_name " + "FROM card_list t " + "JOIN (" + "SELECT r.card_type, COUNT(*) as count "
			+ "FROM card_reg_info r " + "GROUP BY r.card_type " + "ORDER BY count DESC " + "LIMIT 3) as subquery "
			+ "ON t.card_type = subquery.card_type", nativeQuery = true)
	List<String> findTop3CardTypes();
	
	//최근 6개월간 카드 가입자
	@Query("SELECT c.custGender AS custGender, " + "YEAR(r.regDate) AS regYear, " + "MONTH(r.regDate) AS regMonth, "
			+ "COUNT(r.regId) AS regCount " + "FROM CardRegInfoVO r " + "JOIN r.custId c "
			+ "WHERE r.regDate >= :sixMonthsAgo AND r.regDate < :lastMonth "
			+ "GROUP BY c.custGender, regYear, regMonth")
	List<Object[]> countMonthlyRegistrationsByGender(@Param("sixMonthsAgo") LocalDate sixMonthsAgo,
			@Param("lastMonth") LocalDate lastMonth);
	
	//연령대별 사용카드 상위 3개
	@Query(value = "SELECT subquery2.age_range, subquery2.card_name "
			+ "FROM (SELECT age_range, card_name, rn FROM (SELECT c.age_range, t.card_name, ROW_NUMBER() OVER(PARTITION BY c.age_range ORDER BY COUNT(*) DESC) as rn "
			+ "FROM (SELECT cust_id, CASE WHEN YEAR(CURRENT_DATE()) - YEAR(cust_birth) >= 20 AND YEAR(CURRENT_DATE()) - YEAR(cust_birth) < 30 THEN '20대' "
			+ "WHEN YEAR(CURRENT_DATE()) - YEAR(cust_birth) >= 30 AND YEAR(CURRENT_DATE()) - YEAR(cust_birth) < 40 THEN '30대' "
			+ "WHEN YEAR(CURRENT_DATE()) - YEAR(cust_birth) >= 40 AND YEAR(CURRENT_DATE()) - YEAR(cust_birth) < 50 THEN '40대' "
			+ "WHEN YEAR(CURRENT_DATE()) - YEAR(cust_birth) >= 50 AND YEAR(CURRENT_DATE()) - YEAR(cust_birth) < 60 THEN '50대' "
			+ "WHEN YEAR(CURRENT_DATE()) - YEAR(cust_birth) >= 60 AND YEAR(CURRENT_DATE()) - YEAR(cust_birth) < 70 THEN '60대' "
			+ "ELSE '70대 이상' END AS age_range FROM customer) c "
			+ "JOIN card_reg_info r ON r.cust_id = c.cust_id JOIN card_list t ON r.card_type = t.card_type "
			+ "GROUP BY c.age_range, t.card_name) as subquery1 WHERE rn <= 3) as subquery2", nativeQuery = true)
	List<Object[]> findTop3CardTypesByAgeRange();
	
	// 직업별 사용카드 상위 3개
	@Query(value = "SELECT " + "  job_type,"
			+ " SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', 1), ':', 1) AS top1_card,"
			+ " SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', 2), ',', -1) AS top2_card,"
			+ " SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', -1), ':', 1) AS top3_card" + " FROM ("
			+ " SELECT " + " job_type,"
			+ " GROUP_CONCAT(card_name ORDER BY count DESC SEPARATOR ',') as grouped_card_names" + " FROM ("
			+ " SELECT " + " j.job_type, " + " t.card_name, " + " COUNT(*) as count" + " FROM "
			+ " customer c" + " JOIN " + " card_reg_info r ON c.cust_id = r.cust_id" + " JOIN "
			+ " job_list j ON c.job_id = j.job_id" + " JOIN " + " card_list t ON r.card_type = t.card_type"
			+ " GROUP BY " + " j.job_type, t.card_name" + " ) AS subquery" + " GROUP BY job_type"
			+ ") AS final_query", nativeQuery = true)
	List<Object[]> findTop3CardTypesByJobType();
	
	// 연봉별 사용카드 상위 3개
	@Query(value = "SELECT " + "cust_salary,"
			+ "SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', 1), ':', 1) AS top1_card,"
			+ "SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', 2), ',', -1) AS top2_card,"
			+ "SUBSTRING_INDEX(SUBSTRING_INDEX(grouped_card_names, ',', -1), ':', 1) AS top3_card " + "FROM ("
			+ "SELECT " + "cust_salary,"
			+ "GROUP_CONCAT(card_name ORDER BY count DESC SEPARATOR ',') as grouped_card_names " + "FROM (" + "SELECT "
			+ "c.cust_salary, " + "t.card_name, " + "COUNT(*) as count " + "FROM " + "customer c " + "JOIN "
			+ "card_reg_info r ON c.cust_id = r.cust_id " + "JOIN " + "card_list t ON r.card_type = t.card_type "
			+ "GROUP BY " + "c.cust_salary, t.card_name " + ") AS subquery " + "GROUP BY cust_salary "
			+ ") AS final_query", nativeQuery = true)
	List<Object[]> findTop3CardTypesByCustSalary();



	// filter 사용카드
	@Query(value = "SELECT cl.card_name, COUNT(cl.card_type) " + "FROM card_reg_info cri "
			+ "JOIN customer c ON cri.cust_id = c.cust_id "
			+ "JOIN job_list j ON c.job_id = j.job_id " + "JOIN card_list cl ON cl.card_type = cri.card_type "
			+ "WHERE c.cust_gender IN (:gender) "
			+ "AND FLOOR((YEAR(CURRENT_DATE) - YEAR(c.cust_birth))/10) IN (:ageRange) "
			+ "AND c.cust_salary = :salaryRange " + "AND j.job_type = :jobType " + "GROUP BY cl.card_name "
			+ "ORDER BY COUNT(cri.card_type) DESC "
			+ "LIMIT 5", nativeQuery = true)
	List<Object[]> findTop5CardByFilters(@Param("gender") List<String> gender,
			@Param("ageRange") List<Integer> ageRange, @Param("jobType") String jobType,
			@Param("salaryRange") String salaryRange);
	
	
	
	
	


	@Query(value="select count(*) from card_reg_info "
			+ "where expire_date>now()",nativeQuery = true)

	int totalcardregamount();
	

}

