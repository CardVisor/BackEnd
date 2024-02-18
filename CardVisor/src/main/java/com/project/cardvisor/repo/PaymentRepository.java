package com.project.cardvisor.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.PaymentsVO;
import java.sql.Date;

public interface PaymentRepository extends CrudRepository<PaymentsVO, String> {
	//// bohyeon start ////

	@Query(value = "select sum(pay_amount) from payments p \n" + "where reg_id in (select reg_id \n"
			+ "from card_reg_info "
			+ "where card_type = :type and expire_date > :startDate) and pay_date BETWEEN :startDate and :endDate", nativeQuery = true)
	Long getTotalPayNum(@Param("type") Integer type, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	//// bohyeon end ////

	@Query(value = "select * from payments p where p.data_insert_date = :data", nativeQuery = true)
	List<PaymentsVO> findByDataInsertDateForBenefit(@Param("data") Date dataInsertDate);
	/////// 화수쿤 ///////

	@Query(value = "SELECT sum(pay_amount) " + "FROM payments "
			+ "WHERE currency_code = 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 0 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00')  "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 0 MONTH , '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	Long TotalAmountPayments();

	@Query(value = "SELECT sum(pay_amount) " + "FROM payments "
			+ "WHERE currency_code = 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 1 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00') "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 1 MONTH , '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	Long LastMonthTotalAmountPayments();

	@Query(value = "SELECT sum(pay_amount) " + "FROM payments "
			+ "WHERE currency_code != 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 0 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00')  "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 0 MONTH , '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	Long AbroadTotalAmountPayments();

	@Query(value = "SELECT sum(pay_amount) " + "FROM payments "
			+ "WHERE currency_code != 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 1 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00') "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 1 MONTH , '%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	Long AbroadLastMonthTotalAmountPayments();

	/////// 은경 ///////
	// 올해 해외 토탈 결제 금액
	@Query("SELECT SUM(p.payAmount * p.currencyRate)" + " FROM PaymentsVO p" + " WHERE p.currencyCode != 'KRW'"
			+ " AND YEAR(p.payDate) = YEAR(CURRENT_DATE)")
	Long selectTotalOverseasPayment();

	// 전년 월 대비 올해 월 증감 (+/-)
	@Query(value = "WITH thisYear AS (" + " SELECT SUM(p.pay_amount * p.currency_rate) AS currentSum"
			+ " FROM payments p" + " WHERE p.currency_code != 'KRW'" + " AND YEAR(p.pay_date) = YEAR(CURRENT_DATE)"
			+ " AND MONTH(p.pay_date) = :month" + " )," + " lastYear AS ("
			+ " SELECT SUM(p.pay_amount * p.currency_rate) AS lastSum" + " FROM payments p "
			+ " WHERE p.currency_code != 'KRW' " + " AND YEAR(p.pay_date) = YEAR(CURRENT_DATE) - 1"
			+ " AND MONTH(p.pay_date) = :month" + " )" + " SELECT thisYear.currentSum, lastYear.lastSum"
			+ " FROM thisYear, lastYear", nativeQuery = true)
	Map<String, Object> selectDiffPaymentThisYearAndLastYear(@Param("month") int month);

	// 올해 결제 건수가 제일 많은 나라 (순위 리스트업)
	@Query(value = "SELECT COUNT(p.CURRENCY_CODE) AS PAYMENT_CNT"
			+ " , SUM(p.PAY_AMOUNT * p.CURRENCY_RATE) AS TOTAL_PAYMENT" + " , p.CURRENCY_CODE" + "	, p.NATION"
			+ " , DATE_FORMAT(p.PAY_DATE, '%Y') AS CURRENT_YEAR" + " FROM payments p"
			+ " WHERE p.CURRENCY_CODE != 'KRW'" + " AND DATE_FORMAT(p.PAY_DATE, '%Y') = DATE_FORMAT(NOW(), '%Y')"
			+ " GROUP BY p.CURRENCY_CODE" + " ORDER BY PAYMENT_CNT DESC, TOTAL_PAYMENT DESC"
			+ " LIMIT 20", nativeQuery = true)

	List<Map<String, Object>> selectHighestOrderPayment();

	//(차트 데이터) 월별 데이터 추출
	@Query(value="SELECT \r\n"
			+ "    payment_month\r\n"
			+ "    ,nation\r\n"
			+ "    ,age_range\r\n"
			+ "    ,total_payment_count\r\n"
			+ "    ,total_amount\r\n"
			+ "FROM \r\n"
			+ "    (\r\n"
			+ "        SELECT \r\n"
			+ "            payment_month\r\n"
			+ "            ,nation\r\n"
			+ "            ,age_range\r\n"
			+ "            ,payment_count\r\n"
			+ "            ,SUM(total_payment_count) OVER (PARTITION BY payment_month, nation) as total_payment_count\r\n"
			+ "            ,SUM(total_amount) OVER (PARTITION BY payment_month, nation) as total_amount\r\n"
			+ "            ,RANK() OVER (PARTITION BY payment_month, nation ORDER BY payment_count DESC) as rank\r\n"
			+ "        FROM \r\n"
			+ "            (\r\n"
			+ "                SELECT \r\n"
			+ "                    DATE_FORMAT(p.pay_date, '%Y-%m') as payment_month\r\n"
			+ "                    ,p.nation\r\n"
			+ "                    ,CASE \r\n"
			+ "                        WHEN TIMESTAMPDIFF(YEAR, c.cust_birth, CURRENT_DATE) BETWEEN 20 AND 29 THEN '20대'\r\n"
			+ "                        WHEN TIMESTAMPDIFF(YEAR, c.cust_birth, CURRENT_DATE) BETWEEN 30 AND 39 THEN '30대'\r\n"
			+ "                        WHEN TIMESTAMPDIFF(YEAR, c.cust_birth, CURRENT_DATE) BETWEEN 40 AND 49 THEN '40대'\r\n"
			+ "                        WHEN TIMESTAMPDIFF(YEAR, c.cust_birth, CURRENT_DATE) BETWEEN 50 AND 59 THEN '50대'\r\n"
			+ "                        WHEN TIMESTAMPDIFF(YEAR, c.cust_birth, CURRENT_DATE) BETWEEN 60 AND 69 THEN '60대'\r\n"
			+ "                        ELSE '70대 이상'\r\n"
			+ "                    END as age_range\r\n"
			+ "                    ,COUNT(*) as payment_count\r\n"
			+ "                    ,COUNT(*) OVER (PARTITION BY DATE_FORMAT(p.pay_date, '%Y-%m'), p.nation) as total_payment_count\r\n"
			+ "                    ,SUM(p.PAY_AMOUNT * p.CURRENCY_RATE) OVER (PARTITION BY DATE_FORMAT(p.pay_date, '%Y-%m'), p.nation) as total_amount\r\n"
			+ "                FROM \r\n"
			+ "                    customer c\r\n"
			+ "                JOIN \r\n"
			+ "                    card_reg_info cri on c.cust_id = cri.cust_id\r\n"
			+ "                JOIN \r\n"
			+ "                    payments p on cri.reg_id = p.reg_id\r\n"
			+ "                WHERE \r\n"
			+ "                    p.pay_date >= STR_TO_DATE(:start, '%Y-%m-%d') \r\n"
			+ "                AND \r\n"
			+ "                    p.pay_date <= STR_TO_DATE(:end, '%Y-%m-%d') + INTERVAL 1 MONTH - INTERVAL 1 DAY\r\n"
			+ "                AND \r\n"
			+ "                    p.CURRENCY_CODE != 'KRW'\r\n"
			+ "                GROUP BY \r\n"
			+ "                    payment_month, p.nation, age_range\r\n"
			+ "            ) AS subquery1\r\n"
			+ "    ) AS subquery2\r\n"
			+ "WHERE \r\n"
			+ "    rank = 1\r\n"
			+ "ORDER BY\r\n"
			+ "    payment_month DESC", nativeQuery = true)
	//List<Map<String, Object>> selectNationPaymentsDataList();
	List<Map<String, Object>> selectNationPaymentsDataList(@Param("start") LocalDate start, @Param("end") LocalDate end);

	
	
	
	

	//// 지현
	// 고객 성별에 따른 평균 소비금액
	@Query("SELECT c.custGender, AVG(p.payAmount) " + "FROM PaymentsVO p JOIN p.regId r JOIN r.custId c "
			+ "GROUP BY c.custGender")
	List<Object[]> getAveragePaymentAmount();

	// 고객 성별에 따른 상위 3개의 MCC
	@Query(value = "SELECT subquery2.cust_gender, m.ctg_name " + "FROM ("
			+ "    SELECT c.cust_gender, p.mcc_code, ROW_NUMBER() OVER(PARTITION BY c.cust_gender ORDER BY COUNT(*) DESC) as rn "
			+ "    FROM customer c " + "    JOIN card_reg_info r ON c.cust_id = r.cust_id "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY c.cust_gender, p.mcc_code "
			+ ") as subquery " + "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "JOIN ("
			+ "    SELECT c.cust_gender, ROW_NUMBER() OVER(PARTITION BY c.cust_gender ORDER BY COUNT(*) DESC) as rn "
			+ "    FROM customer c " + "    JOIN card_reg_info r ON c.cust_id = r.cust_id "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY c.cust_gender "
			+ ") as subquery2 ON subquery.cust_gender = subquery2.cust_gender AND subquery.rn <= 3 " + "UNION "
			+ "SELECT 'all' as cust_gender, m.ctg_name " + "FROM ("
			+ "    SELECT p.mcc_code, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) as rn " + "    FROM card_reg_info r "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY p.mcc_code " + ") as subquery "
			+ "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "WHERE subquery.rn <= 3", nativeQuery = true)
	List<Object[]> getTop3MccCodeByGender();

	// 연령대별의 평균 소비금액
	@Query(value = "SELECT age_range, AVG(pay_amount) FROM ( " + "SELECT CASE  "
			+ "WHEN age >= 20 AND age < 30 THEN '20대' " + "WHEN age >= 30 AND age < 40 THEN '30대' "
			+ "WHEN age >= 40 AND age < 50 THEN '40대' " + "WHEN age >= 50 AND age < 60 THEN '50대' "
			+ "WHEN age >= 60 AND age < 70 THEN '60대' " + "ELSE '70대 이상' END as age_range, "
			+ "AVG(pay_amount) as pay_amount " + "FROM ( SELECT p.*, YEAR(CURRENT_DATE) - YEAR(c.cust_birth) as age "
			+ "FROM payments p " + "JOIN card_reg_info cri ON p.reg_id = cri.reg_id "
			+ "JOIN customer c ON cri.cust_id = c.cust_id ) as subquery " + "GROUP BY age_range " + "UNION "
			+ "SELECT 'all' as age_range, AVG(pay_amount) as pay_amount " + "FROM payments p ) as all_data "
			+ "GROUP BY age_range "
			+ "ORDER BY CASE WHEN age_range = 'all' THEN 0 ELSE 1 END, age_range", nativeQuery = true)
	List<Map<String, Object>> findAveragePaymentByAgeRange();

	// 연령대별에 따른 상위 3개의 MCC
	@Query(value = "" + "(SELECT subquery.age_range, m.ctg_name " + "FROM ("
			+ "SELECT subquery1.age_range, subquery1.mcc_code, ROW_NUMBER() OVER(PARTITION BY subquery1.age_range ORDER BY subquery1.count DESC) as rn "
			+ "FROM (" + "SELECT CASE "
			+ "WHEN YEAR(CURRENT_DATE) - YEAR(c.cust_birth) >= 20 AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) < 30 THEN '20대' "
			+ "WHEN YEAR(CURRENT_DATE) - YEAR(c.cust_birth) >= 30 AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) < 40 THEN '30대' "
			+ "WHEN YEAR(CURRENT_DATE) - YEAR(c.cust_birth) >= 40 AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) < 50 THEN '40대' "
			+ "WHEN YEAR(CURRENT_DATE) - YEAR(c.cust_birth) >= 50 AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) < 60 THEN '50대' "
			+ "WHEN YEAR(CURRENT_DATE) - YEAR(c.cust_birth) >= 60 AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) < 70 THEN '60대' "
			+ "ELSE '70대 이상' END as age_range, p.mcc_code, COUNT(*) as count " + "FROM customer c "
			+ "JOIN card_reg_info r ON c.cust_id = r.cust_id " + "JOIN payments p ON r.reg_id = p.reg_id "
			+ "GROUP BY age_range, p.mcc_code " + ") as subquery1 " + ") as subquery "
			+ "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "WHERE subquery.rn <= 3 " + "UNION "
			+ "SELECT 'all' as age_range, m.ctg_name " + "FROM ("
			+ "SELECT p.mcc_code, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) as rn " + "FROM card_reg_info r "
			+ "JOIN payments p ON r.reg_id = p.reg_id " + "GROUP BY p.mcc_code " + ") as subquery_all "
			+ "JOIN mcc m ON m.mcc_code = subquery_all.mcc_code " + "WHERE subquery_all.rn <= 3)", nativeQuery = true)
	List<Object[]> findTopMccCodes();


	// 직업별 평균 소비금액
	@Query(value = "(SELECT j.job_type, AVG(p.pay_amount) "
			+ "FROM payments p JOIN card_reg_info r ON p.reg_id = r.reg_id JOIN customer c ON r.cust_id = c.cust_id JOIN job_list j ON c.job_id = j.job_id "
			+ "GROUP BY j.job_type) " + "UNION ALL " + "(SELECT 'all', AVG(p.pay_amount) "
			+ "FROM payments p)", nativeQuery = true)
	List<Object[]> AveragePaymentByJobTypeAndAll();

	// 직업별 주 사용처 상위 3개
	@Query(value = "SELECT subquery2.job_type, m.ctg_name " + "FROM ("
			+ " SELECT j.job_type, p.mcc_code, ROW_NUMBER() OVER(PARTITION BY j.job_type ORDER BY COUNT(*) DESC) as rn "
			+ " FROM job_list j " + "    JOIN customer c ON j.job_id = c.job_id "
			+ " JOIN card_reg_info r ON c.cust_id = r.cust_id " + "    JOIN payments p ON r.reg_id = p.reg_id "
			+ " GROUP BY j.job_type, p.mcc_code " + ") as subquery " + "JOIN mcc m ON m.mcc_code = subquery.mcc_code "
			+ "JOIN (" + " SELECT j.job_type, ROW_NUMBER() OVER(PARTITION BY j.job_type ORDER BY COUNT(*) DESC) as rn "
			+ " FROM job_list j " + "    JOIN customer c ON j.job_id = c.job_id "
			+ " JOIN card_reg_info r ON c.cust_id = r.cust_id " + "    JOIN payments p ON r.reg_id = p.reg_id "
			+ " GROUP BY j.job_type " + ") as subquery2 ON subquery.job_type = subquery2.job_type AND subquery.rn <= 3 "
			+ "UNION " + "SELECT 'all' as job_type, m.ctg_name " + "FROM ("
			+ " SELECT p.mcc_code, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) as rn " + " FROM card_reg_info r "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + " GROUP BY p.mcc_code " + ") as subquery "
			+ "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "WHERE subquery.rn <= 3", nativeQuery = true)
	List<Object[]> getTop3MccCodeByJobType();
	
	//연봉별 평균 소비금액
	@Query(value = "(SELECT c.cust_salary, AVG(p.pay_amount) "
	        + "FROM payments p JOIN card_reg_info r ON p.reg_id = r.reg_id JOIN customer c ON r.cust_id = c.cust_id "
	        + "GROUP BY c.cust_salary) " 
	        + "UNION ALL " 
	        + "(SELECT 'all', AVG(p.pay_amount) "
	        + "FROM payments p)", nativeQuery = true)
	List<Object[]> paymentBySalaryRangeAndAll();
	
	//연봉별 상위 3개 mcc
	@Query(value = "SELECT subquery2.cust_salary, m.ctg_name " + "FROM ("
			+ "    SELECT c.cust_salary, p.mcc_code, ROW_NUMBER() OVER(PARTITION BY c.cust_salary ORDER BY COUNT(*) DESC) as rn "
			+ "    FROM customer c " + "    JOIN card_reg_info r ON c.cust_id = r.cust_id "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY c.cust_salary, p.mcc_code "
			+ ") as subquery " + "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "JOIN ("
			+ "    SELECT c.cust_salary, ROW_NUMBER() OVER(PARTITION BY c.cust_salary ORDER BY COUNT(*) DESC) as rn "
			+ "    FROM customer c " + "    JOIN card_reg_info r ON c.cust_id = r.cust_id "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY c.cust_salary "
			+ ") as subquery2 ON subquery.cust_salary = subquery2.cust_salary AND subquery.rn <= 3 " + "UNION "
			+ "SELECT 'all' as cust_salary, m.ctg_name " + "FROM ("
			+ "    SELECT p.mcc_code, ROW_NUMBER() OVER(ORDER BY COUNT(*) DESC) as rn " + "    FROM card_reg_info r "
			+ "    JOIN payments p ON r.reg_id = p.reg_id " + "    GROUP BY p.mcc_code " + ") as subquery "
			+ "JOIN mcc m ON m.mcc_code = subquery.mcc_code " + "WHERE subquery.rn <= 3", nativeQuery = true)
	List<Object[]> getTop3MccCodeByCustSalary();
	
	
	
	// filter 평균 소비금액
	@Query(value = "SELECT SUM(p.pay_amount) / COUNT(DISTINCT cri.cust_id) " + "FROM payments p "
			+ "JOIN card_reg_info cri ON p.reg_id = cri.reg_id " + "JOIN customer c ON cri.cust_id = c.cust_id "
			+ "JOIN job_list j ON c.job_id = j.job_id " + "WHERE c.cust_gender = :gender "
			+ "AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) BETWEEN " + "CASE WHEN :ageRange = '20대' THEN 20 "
			+ "     WHEN :ageRange = '30대' THEN 30 " + "     WHEN :ageRange = '40대' THEN 40 "
			+ "     WHEN :ageRange = '50대' THEN 50 " + "     WHEN :ageRange = '60대' THEN 60 "
			+ "     WHEN :ageRange = '70대 이상' THEN 70 " + "END " + "AND " + "CASE WHEN :ageRange = '20대' THEN 29 "
			+ "     WHEN :ageRange = '30대' THEN 39 " + "     WHEN :ageRange = '40대' THEN 49 "
			+ "     WHEN :ageRange = '50대' THEN 59 " + "     WHEN :ageRange = '60대' THEN 69 "
			+ "     WHEN :ageRange = '70대 이상' THEN 999 " + // 매우 큰 값으로 설정하여 모든 연령대를 포함
			"END " + "AND j.job_type = :jobType " + "AND c.cust_salary = :salaryRange ", nativeQuery = true)
	Double findAveragePaymentByFilters(@Param("gender") String gender, @Param("ageRange") String ageRange,
			@Param("jobType") String jobType, @Param("salaryRange") String salaryRange);
	
	// filter 사용처
	@Query(value = "SELECT m.ctg_name " + "FROM payments p " + "JOIN card_reg_info cri ON p.reg_id = cri.reg_id "
			+ "JOIN customer c ON cri.cust_id = c.cust_id " + "JOIN job_list j ON c.job_id = j.job_id "
			+ "JOIN mcc m ON p.mcc_code = m.mcc_code " + "WHERE c.cust_gender = :gender "
			+ "AND YEAR(CURRENT_DATE) - YEAR(c.cust_birth) BETWEEN " + "CASE WHEN :ageRange = '20대' THEN 20 "
			+ "     WHEN :ageRange = '30대' THEN 30 " + "     WHEN :ageRange = '40대' THEN 40 "
			+ "     WHEN :ageRange = '50대' THEN 50 " + "     WHEN :ageRange = '60대' THEN 60 "
			+ "     WHEN :ageRange = '70대 이상' THEN 70 " + "END " + "AND " + "CASE WHEN :ageRange = '20대' THEN 29 "
			+ "     WHEN :ageRange = '30대' THEN 39 " + "     WHEN :ageRange = '40대' THEN 49 "
			+ "     WHEN :ageRange = '50대' THEN 59 " + "     WHEN :ageRange = '60대' THEN 69 "
			+ "     WHEN :ageRange = '70대 이상' THEN 999 " + // 매우 큰 값으로 설정하여 모든 연령대를 포함
			"END " + "AND j.job_type = :jobType " + "AND c.cust_salary = :salaryRange " + "GROUP BY m.ctg_name "
			+ "ORDER BY COUNT(m.ctg_name) DESC " + "LIMIT 5", nativeQuery = true)
	List<String> findTop5CategoriesByFilters(@Param("gender") String gender, @Param("ageRange") String ageRange,
			@Param("jobType") String jobType, @Param("salaryRange") String salaryRange);
	
	
	//WorldMap

	// select 건수, 총금액, 연령대
	// 22,23,24년도 월별

	// 용수
	// 6개월 전부터 매달 총결제금액
	@Query(value = "SELECT " + "  DATE_FORMAT(pay_date, '%Y-%m') AS month, " + "  SUM(pay_amount) AS total_amount "
			+ "FROM payments " + "WHERE pay_date > DATE_SUB(CURRENT_DATE, INTERVAL 5 MONTH) " + "GROUP BY month "
			+ "ORDER BY month asc ", nativeQuery = true)
	List<Map<String, Object>> selectPerMonthamount();

	@Query(value = "SELECT  " + "  DATE_FORMAT(pay_date, '%Y-%m') AS month, " + "  SUM(pay_amount) AS total_amount "
			+ "FROM payments " + "WHERE pay_date > DATE_SUB(CURRENT_DATE,INTERVAL 17 MONTH) AND "
			+ "      pay_date < DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR) " + "GROUP BY month "
			+ "ORDER BY month", nativeQuery = true)
	List<Map<String, Object>> selectLastYearPerMonthamount();

	@Query(value = "SELECT  " + "  SUM(pay_amount) AS total_amount " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE , INTERVAL 5 MONTH) ", nativeQuery = true)
	Long perMonthTotalAmount();

	@Query(value = "SELECT  " + "  SUM(pay_amount) AS total_amount " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE,INTERVAL 17 MONTH) AND "
			+ "      pay_date < DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR)", nativeQuery = true)
	Long lastYearPerMonthTotalAmount();

	@Query(value = "SELECT " + "  WEEK(pay_date) AS week, " + "  SUM(pay_amount) AS total_amount " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 6 WEEK) " + "GROUP BY week "
			+ "ORDER BY week", nativeQuery = true)
	List<Map<String, Object>> selectPerWeeklyamount();

	@Query(value = "SELECT " + "  WEEK(pay_date) AS week, " + "  SUM(pay_amount) AS total_amount " + "FROM payments"
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 52 WEEK)"
			+ "and	  pay_date < DATE_SUB(CURRENT_DATE, INTERVAL 47 WEEK) " + "GROUP BY week "
			+ "ORDER BY week", nativeQuery = true)
	List<Map<String, Object>> selectLastYearPerWeeklyamount();

	@Query(value = "SELECT " + "  SUM(pay_amount) AS total_amount " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 6 WEEK)", nativeQuery = true)
	Long perWeekTotalAmount();

	@Query(value = "SELECT " + "  SUM(pay_amount) AS total_amount " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 52 WEEK) "
			+ "and	  pay_date < DATE_SUB(CURRENT_DATE, INTERVAL 47 WEEK)", nativeQuery = true)
	Long lastYearPerWeekTotalAmount();

	// 월거래건수
	@Query(value = "SELECT " + "  DATE_FORMAT(pay_date, '%Y-%m') AS month," + "  count(*) As transaction "
			+ "FROM payments " + "WHERE pay_date >= DATE_SUB(CURRENT_DATE , INTERVAL 5 MONTH) " + "GROUP BY month "
			+ "ORDER BY month", nativeQuery = true)
	List<Map<String, Object>> selectPerMonthtransaction();

	@Query(value = "SELECT " + "  count(*) As transaction " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE , INTERVAL 5 MONTH)", nativeQuery = true)
	int selectMonthtransaction();

	// 필요한가?..
	@Query(value = "SELECT \r\n" + "  DATE_FORMAT(pay_date, '%Y-%m') AS month, " + "  count(*) As transaction "
			+ "FROM payments " + "WHERE pay_date >= DATE_SUB(CURRENT_DATE,INTERVAL 17 MONTH) AND "
			+ "      pay_date < DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR) " + "GROUP BY month "
			+ "ORDER BY month", nativeQuery = true)
	List<Map<String, Object>> selectLastYearMonthtransaction();

	// 주간
	@Query(value = "SELECT\r\n" + "  WEEK(pay_date) AS week, " + "    count(*) As transaction " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 6 WEEK) " + "GROUP BY week "
			+ "ORDER BY week", nativeQuery = true)
	List<Map<String, Object>> selectPerWeeklytransaction();

	@Query(value = "SELECT  " + "  count(*) As transaction " + "FROM payments "
			+ "WHERE pay_date >= DATE_SUB(CURRENT_DATE, INTERVAL 6 WEEK)", nativeQuery = true)
	int selectWeektransaction();

	@Query(value = "select cl.card_name, count(cri.card_type) as count, sum(p.pay_amount) as sumamount, cl.card_annual_fee  "
			+ "from payments p,card_reg_info cri, card_list cl  "
			+ "where p.reg_id = cri.reg_id and cri.card_type = cl.card_type " + "group by cri.card_type  "
			+ "order by sumamount desc " + "limit 5", nativeQuery = true)
	List<Map<String, Object>> PayAmountTop5Card();

	@Query(value = "SELECT c.currency_code, c.currency_nation,count(p.pay_amount) as countable, sum(p.pay_amount) AS amountable "
			+ "FROM (select distinct currency_code, currency_nation from currency) c "
			+ "LEFT OUTER JOIN payments p ON p.currency_code = c.currency_code " + "where c.currency_code != 'KRW' "
			+ "GROUP BY c.currency_code  " + "order by amountable desc " + "limit 5", nativeQuery = true)
	List<Map<String, Object>> AbroadPayAmountTop5Card();

	@Query(value = "SELECT cbl.card_name ,b.benefit_detail ,p.applied_benefit_id, sum(p.benefit_amount) as total "
			+ "FROM " + "(select DISTINCT cl.card_type, cl.card_name, cb.benefit_id from card_list cl "
			+ "join card_benefit cb on cl.card_type = cb.card_type) cbl left join benefit b "
			+ "on cbl.benefit_id = b.benefit_id left join card_reg_info cri "
			+ "on cbl.card_type = cri.card_type left join payments p on cri.reg_id = p.reg_id "
			+ "WHERE cbl.benefit_id = p.applied_benefit_id " + "GROUP BY p.applied_benefit_id " + "order by total desc "
			+ "limit 5", nativeQuery = true)
	List<Map<String, Object>> benefitTop5Card();

	@Query(value = "select sum(benefit_amount) from payments p", nativeQuery = true)
	int benefitTotalAmount();
}
