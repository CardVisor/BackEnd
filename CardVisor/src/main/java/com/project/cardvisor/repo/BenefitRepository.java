package com.project.cardvisor.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.BenefitVO;

import jakarta.persistence.Tuple;

public interface BenefitRepository extends CrudRepository<BenefitVO, Integer>{
	
	@Query(value= "select b.* from card_benefit cb "
			+ "join card_reg_info cri on cb.card_type = cri.card_type "
			+ "join benefit b on cb.benefit_id = b.benefit_id "
			+ "where cri.reg_id = :reg_id "
			+ "and b.mcc_code = :mcc_code "
			+ "ORDER by b.benefit_pct desc "
			+ "limit 1", nativeQuery = true)
	BenefitVO selectBenefitPctAndId(@Param("reg_id")String reg_id, @Param("mcc_code")String mcc_code);
	
	@Query(value = "select b.mcc_code, m.ctg_name, b.benefit_detail, sum(p.benefit_amount) benefit_amount_sum, b.benefit_id,  b.benefit_pct, b.interest_id "
			+ "from payments p  "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id "
			+ "join card_benefit cb on cb.card_type = cri.card_type "
			+ "join benefit b on cb.benefit_id = b.benefit_id "
			+ "JOIN mcc m ON b.mcc_code = m.mcc_code "
			+ "WHERE p.pay_date < CURRENT_TIMESTAMP() "
			+ "AND p.benefit_amount > 0 "
			+ "and b.benefit_id = p.applied_benefit_id "
			+ "group BY b.benefit_id "
			+ "ORDER BY b.mcc_code asc, sum(p.benefit_amount) desc, b.benefit_id asc", nativeQuery = true)
	List<Tuple> findByMCC();
	
	@Query(value = "select b.mcc_code, m.ctg_name, b.benefit_detail, sum(p.benefit_amount) benefit_amount_sum, b.benefit_id,  b.benefit_pct, b.interest_id "
			+ "from payments p  "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id "
			+ "join card_benefit cb on cb.card_type = cri.card_type "
			+ "join benefit b on cb.benefit_id = b.benefit_id "
			+ "JOIN mcc m ON b.mcc_code = m.mcc_code "
			+ "WHERE DATE_FORMAT(p.pay_date, '%Y-%m') LIKE :date "
			+ "AND p.benefit_amount > 0 "
			+ "and b.benefit_id = p.applied_benefit_id "
			+ "group BY b.benefit_id "
			+ "ORDER BY b.mcc_code asc, sum(p.benefit_amount) desc, b.benefit_id asc", nativeQuery = true)
	List<Tuple> findByMCCWithDate(@Param("date") String date);
	
	@Query(value = "SELECT DISTINCT(m.ctg_name) "
			+ "FROM payments p join mcc m "
			+ "on p.mcc_code = m.mcc_code "
			+ "WHERE p.benefit_amount > 0 "
			+ "AND p.pay_date < CURRENT_TIMESTAMP() "
			+ "ORDER BY p.mcc_code asc", nativeQuery = true)
	List<String> findDistinctMCC();
	
	@Query(value = "SELECT b.benefit_id AS benefit_id, b.benefit_detail AS benefit_detail, b.benefit_pct AS benefit_pct, "
			+ "IFNULL(p.sum_benefit_amount,0) AS sum_benefit_amount, "
			+ "IFNULL(p.count_benefit_used,0) AS count_benefit_used, "
			+ "IFNULL(p.count_using_people,0) AS count_using_people "
			+ "FROM benefit b "
			+ "JOIN "
			+ "(SELECT "
			+ "applied_benefit_id, "
			+ "SUM(benefit_amount) AS sum_benefit_amount, "
			+ "COUNT(benefit_amount) AS count_benefit_used, "
			+ "COUNT(DISTINCT reg_id) AS count_using_people "
			+ "FROM payments "
			+ "WHERE :date is null or DATE_FORMAT(pay_date, '%Y-%m') LIKE :date "
			+ "GROUP BY applied_benefit_id) p "
			+ "ON b.benefit_id = p.applied_benefit_id "
			+ "WHERE b.mcc_code = :mcc_code "
			+ "ORDER BY b.benefit_id ASC", nativeQuery = true)
	List<Map<String, Object>> benefitInfoAndCalData(@Param("mcc_code") String mcc_code, @Param("date") String date);
	
	@Query(value = "select b.benefit_id, count(cb.card_type) related_card_cnt, AVG(cl.card_annual_fee) avg_annual_fee "
			+ "from benefit b "
			+ "join card_benefit cb on b.benefit_id = cb.benefit_id "
			+ "join card_list cl on cb.card_type = cl.card_type "
			+ "WHERE b.mcc_code =:mcc_code "
			+ "GROUP BY b.benefit_id "
			+ "ORDER BY b.benefit_id asc", nativeQuery = true)
	List<Map<String, Object>> cardCalData(@Param("mcc_code") String mcc_code);
	
	@Query(value = "select b.benefit_id, cb.card_type, cl.card_name "
			+ "from card_benefit cb "
			+ "join benefit b on cb.benefit_id = b.benefit_id "
			+ "join card_list cl on cl.card_type = cb.card_type "
			+ "where b.benefit_id in "
			+ "(select b.benefit_id "
			+ "from benefit b "
			+ "WHERE b.mcc_code =:mcc_code) "
			+ "order BY benefit_id", nativeQuery = true)
	List<Map<String, Object>> cardDetailRelatedBenefit(@Param("mcc_code") String mcc_code);
	
	@Query(value = "select sum(p.benefit_amount) as cur_sum, count(p.benefit_amount) as cur_cnt, count(DISTINCT p.reg_id) as cur_use "
			+ "from payments p "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id "
			+ "WHERE cri.card_type =:card_type "
			+ "AND (:date is null or DATE_FORMAT(p.pay_date, '%Y-%m') LIKE :date) "
			+ "and p.applied_benefit_id =:applied_benefit_id", nativeQuery = true)
	Map<String, Object> cardComparison(@Param("card_type") int card_type, @Param("applied_benefit_id") int applied_benefit_id, @Param("date") String date);
	
	@Query(value = "select b.benefit_id, cl.card_type, cl.card_name, cl.card_annual_fee "
			+ "from card_benefit cb "
			+ "join benefit b on cb.benefit_id = b.benefit_id "
			+ "join card_list cl on cl.card_type = cb.card_type "
			+ "where b.benefit_id in :benefit_list "
			+ "order BY benefit_id", nativeQuery = true)
	List<Map<String, Object>> cardDetailRelatedBenefitForFilteredAction(@Param("benefit_list") List<Integer> benefit_list);

	@Query(value = "select cri.card_type, cl.card_annual_fee, cl.card_name, cl.card_img_url, p.applied_benefit_id benefit_id, count(p.applied_benefit_id) cnt_benefit, sum(p.benefit_amount) sum_benefit "
			+ "from payments p "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id "
			+ "join card_list cl on cri.card_type = cl.card_type "
			+ "where cri.card_type in "
			+ "("
			+ "select cb.card_type "
			+ "from card_benefit cb "
			+ "where cb.benefit_id = :benefit_id "
			+ ") "
			+ "and p.reg_id in :regIds "
			+ "and p.benefit_amount > 0 "
			+ "and p.applied_benefit_id = :benefit_id "
			+ "and DATE_FORMAT(p.pay_date,'%Y') = '2023' "
			+ "GROUP BY cri.card_type", nativeQuery = true)
	List<Tuple> currentBenefitQueryForRecommend(@Param("benefit_id") Integer benefit_id,@Param("regIds") List<String> regIds);

	@Query(value = "select count(p.pay_amount) pay_cnt, sum(p.pay_amount) pay_sum "
			+ "from payments p "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id  "
			+ "where DATE_FORMAT(p.pay_date,'%Y') = '2023' "
			+ "and cri.card_type = :card_type", nativeQuery = true)
	Map<String, Object> totalPaybyCurCard(@Param("card_type") Integer card_type);
	
	@Query(value="SELECT "
			+ "cl.card_type, "
			+ "p.applied_benefit_id, "
			+ "b.benefit_pct, "
			+ "COUNT(p.applied_benefit_id) benefit_usage " 
			+ "FROM "
			+ "    payments p "
			+ "JOIN "
			+ "    card_reg_info cri ON p.reg_id = cri.reg_id "
			+ "JOIN "
			+ "    card_list cl ON cl.card_type = cri.card_type "
			+ "JOIN "
			+ "    benefit b ON p.applied_benefit_id = b.benefit_id "
			+ "WHERE "
			+ "    p.benefit_amount > 0 "
			+ "GROUP BY "
			+ "cl.card_type, p.applied_benefit_id, b.benefit_pct "
			+ "order by cl.card_type asc", nativeQuery = true)
	List<Map<String, Object>> benefit_usage_count();

	@Query(value="select cl.card_type, cl.card_name, cl.card_annual_fee, COUNT(cl.card_name) card_usage "
			+ "from payments p "
			+ "join card_reg_info cri on p.reg_id = cri.reg_id "
			+ "join card_list cl on cl.card_type = cri.card_type "
			+ "GROUP BY cl.card_name "
			+ "ORDER BY cl.card_type asc", nativeQuery = true)
	List<Map<String, Object>> total_pay_count();
}
