package com.project.cardvisor.repo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.CustomerVO;

public interface CustomerRepository extends CrudRepository<CustomerVO, String> {

	// 직업별 인원 조회
	@Query("SELECT c.jobId.jobType as jobType, COUNT(c) as count FROM CustomerVO c GROUP BY c.jobId.jobType")
	List<Map<String, Object>> findAllJobTypes();

	// 연봉별 인원 조회
	@Query("SELECT custSalary as custsalary, COUNT(*) as count FROM CustomerVO GROUP BY custSalary")
	List<Object[]> findAllCustSalary();
	
	// 전체 고객 수
	@Query("SELECT COUNT(c) FROM CustomerVO c")
	long getTotalCustomers();
 
	// 남성 고객 수
	@Query("SELECT COUNT(c) FROM CustomerVO c WHERE c.custGender = '남'")
	long getMaleCustomers();

	// 여성 고객 수
	@Query("SELECT COUNT(c) FROM CustomerVO c WHERE c.custGender = '여'")
	long getFemaleCustomers();

	// 전체(남,여)평균 연령
	@Query("SELECT CAST(c.custGender AS string), AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c GROUP BY c.custGender "
			+ "UNION ALL " + "SELECT 'all', AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c")
	List<Object[]> getAverageAge();

	// 가장 많은 수의 고객을 가진 연봉 카테고리
	@Query(value = "(SELECT 'all', cust_salary FROM customer GROUP BY cust_salary ORDER BY COUNT(*) DESC LIMIT 1) "
			+ "UNION ALL "
			+ "(SELECT 'male', cust_salary FROM customer WHERE cust_gender = '남' GROUP BY cust_salary ORDER BY COUNT(*) DESC LIMIT 1) "
			+ "UNION ALL "
			+ "(SELECT 'female', cust_salary FROM customer WHERE cust_gender = '여' GROUP BY cust_salary ORDER BY COUNT(*) DESC LIMIT 1)", nativeQuery = true)
	List<Object[]> getMostCommonSalary();

	// 연령대별 고객 수 조회
	@Query(value = "SELECT " + "SUM(CASE WHEN age >= 20 AND age < 30 THEN 1 ELSE 0 END) AS '20대', "
			+ "SUM(CASE WHEN age >= 30 AND age < 40 THEN 1 ELSE 0 END) AS '30대', "
			+ "SUM(CASE WHEN age >= 40 AND age < 50 THEN 1 ELSE 0 END) AS '40대', "
			+ "SUM(CASE WHEN age >= 50 AND age < 60 THEN 1 ELSE 0 END) AS '50대', "
			+ "SUM(CASE WHEN age >= 60 AND age < 70 THEN 1 ELSE 0 END) AS '60대', "
			+ "SUM(CASE WHEN age >= 70 THEN 1 ELSE 0 END) AS '70대 이상' " + "FROM ( "
			+ "SELECT (YEAR(CURRENT_DATE) - YEAR(c.cust_birth)) - "
			+ "(CASE WHEN (MONTH(CURRENT_DATE) < MONTH(c.cust_birth)) OR "
			+ "(MONTH(CURRENT_DATE) = MONTH(c.cust_birth) AND DAY(CURRENT_DATE) < DAY(c.cust_birth)) THEN 1 ELSE 0 END) AS age "
			+ "FROM customer c " + ") c;", nativeQuery = true)
	Map<String, BigDecimal> findAgeGroups();

	// 연령대별 고객 연령 총합
	@Query(value = "SELECT " + "SUM(CASE WHEN age >= 20 AND age < 30 THEN age ELSE 0 END) AS '20대', "
			+ "SUM(CASE WHEN age >= 30 AND age < 40 THEN age ELSE 0 END) AS '30대', "
			+ "SUM(CASE WHEN age >= 40 AND age < 50 THEN age ELSE 0 END) AS '40대', "
			+ "SUM(CASE WHEN age >= 50 AND age < 60 THEN age ELSE 0 END) AS '50대', "
			+ "SUM(CASE WHEN age >= 60 AND age < 70 THEN age ELSE 0 END) AS '60대', "
			+ "SUM(CASE WHEN age >= 70 THEN age ELSE 0 END) AS '70대 이상' " + "FROM ( "
			+ "SELECT (YEAR(CURRENT_DATE) - YEAR(c.cust_birth)) - "
			+ "(CASE WHEN (MONTH(CURRENT_DATE) < MONTH(c.cust_birth)) OR "
			+ "(MONTH(CURRENT_DATE) = MONTH(c.cust_birth) AND DAY(CURRENT_DATE) < DAY(c.cust_birth)) THEN 1 ELSE 0 END) AS age "
			+ "FROM customer c " + ") c;", nativeQuery = true)
	Map<String, BigDecimal> findAgeSums();

	// 연령대별 가장 많은 수의 고객을 가진 연봉 카테고리
	@Query(value = "SELECT age_range, salary, count " + "FROM (" + "  SELECT age_range, salary, count, "
			+ "ROW_NUMBER() OVER (PARTITION BY age_range ORDER BY count DESC) as rn " + " FROM (" + "SELECT CASE "
			+ " WHEN age BETWEEN 20 AND 29 THEN '20대' " + "WHEN age >= 30 AND age < 40 THEN '30대' "
			+ " WHEN age >= 40 AND age < 50 THEN '40대' " + "WHEN age >= 50 AND age < 60 THEN '50대' "
			+ " WHEN age >= 60 AND age < 70 THEN '60대' " + "ELSE '70대 이상' END as age_range, "
			+ "    cust_salary as salary, COUNT(*) as count "
			+ "FROM (SELECT cust_salary, YEAR(CURRENT_DATE) - YEAR(cust_birth) as age FROM customer) as subquery "
			+ "GROUP BY age_range, salary " + "  ) as subquery2 " + " UNION ALL "
			+ "SELECT 'all' as age_range, salary, count, " + " ROW_NUMBER() OVER (ORDER BY count DESC) as rn "
			+ "FROM (" + " SELECT cust_salary as salary, COUNT(*) as count " + " FROM customer " + "GROUP BY salary "
			+ "  ) as subquery3 " + ") as subquery4 " + "WHERE rn = 1", nativeQuery = true)
	List<Object[]> getTopSalaryByAgeRange();

	// 직종별 고객 인원수와 전체 고객 인원수
	@Query(value = "(SELECT j.job_type, COUNT(*) " + "FROM customer c "
			+ "INNER JOIN job_list j ON c.job_id = j.job_id " + "GROUP BY j.job_type) " + "UNION ALL "
			+ "(SELECT 'all', COUNT(*) " + "FROM customer)", nativeQuery = true)
	List<Object[]> countByJobTypeAndAll();

	// 직종별 평균 연령
	@Query("SELECT j.jobType, AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c JOIN c.jobId j GROUP BY j.jobType "
			+ "UNION ALL " + "SELECT 'all', AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c")
	List<Object[]> getAverageAgeByJobTypeAndAll();
	
	// 직종별 가장 많은 고객이 속한 연봉 범위
	@Query(value = "(SELECT j.job_type, (SELECT c.cust_salary FROM customer c WHERE c.job_id = j.job_id GROUP BY c.cust_salary ORDER BY COUNT(*) DESC LIMIT 1) as most_common_salary_range "
			+ "FROM job_list j) " + "UNION ALL "
			+ "(SELECT 'all', (SELECT c.cust_salary FROM customer c GROUP BY c.cust_salary ORDER BY COUNT(*) DESC LIMIT 1))", nativeQuery = true)
	List<Object[]> MostCommonByJobTypeAndAll();

	// 연봉별 고객 인원수
	@Query(value = "(SELECT cust_salary as custsalary, COUNT(*) as count FROM customer GROUP BY cust_salary) "
			+ "UNION ALL " + "(SELECT 'all', COUNT(*) FROM customer)", nativeQuery = true)
	List<Object[]> findAllCustSalaryAndAll();

	// 연봉별 고객 평균 연령
	@Query("SELECT c.custSalary, AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c GROUP BY c.custSalary "
			+ "UNION ALL " + "SELECT 'all', AVG(TIMESTAMPDIFF(YEAR, c.custBirth, CURDATE())) FROM CustomerVO c")
	List<Object[]> getAgeSalaryRangeAndAll();

	// 연봉별 조회
	@Query(value = "SELECT 'all' as custsalary, " + "(SELECT cust_salary " + "FROM ( "
			+ "SELECT cust_salary, COUNT(*) as count " + "FROM customer " + "GROUP BY cust_salary "
			+ "ORDER BY count DESC " + "LIMIT 1) AS subquery) AS count " + "UNION ALL "
			+ "SELECT cust_salary as custsalary, cust_salary as count " + "FROM customer "
			+ "GROUP BY cust_salary", nativeQuery = true)
	List<Object[]> findCustSalaryData();



	// filter 고객 수
	@Query(value = "SELECT COUNT(DISTINCT c.cust_id) " + "FROM customer c "
			+ "JOIN card_reg_info cri ON c.cust_id = cri.cust_id " + "JOIN payments p ON cri.reg_id = p.reg_id "
			+ "JOIN job_list j ON c.job_id = j.job_id " + "WHERE c.cust_gender IN (:gender) "
			+ "AND FLOOR((YEAR(CURRENT_DATE) - YEAR(c.cust_birth))/10) IN (:ageRange) "
			+ "AND c.cust_salary = :salaryRange " + "AND j.job_type = :jobType", nativeQuery = true)
	Long countDistinctByFilters(@Param("gender") List<String> gender, @Param("ageRange") List<Integer> ageRange,
			@Param("jobType") String jobType, @Param("salaryRange") String salaryRange);
	
	// filter 평균 연령
	@Query(value = "SELECT AVG(YEAR(CURRENT_DATE) - YEAR(c.cust_birth)) " + "FROM customer c "
			+ "JOIN card_reg_info cri ON c.cust_id = cri.cust_id " + "JOIN payments p ON cri.reg_id = p.reg_id "
			+ "JOIN job_list j ON c.job_id = j.job_id " + "WHERE c.cust_gender IN (:gender) "
			+ "AND FLOOR((YEAR(CURRENT_DATE) - YEAR(c.cust_birth))/10) IN (:ageRange) "
			+ "AND c.cust_salary = :salaryRange " + "AND j.job_type = :jobType", nativeQuery = true)
	Double averageAgeByFilters(@Param("gender") List<String> gender, @Param("ageRange") List<Integer> ageRange,
			@Param("jobType") String jobType, @Param("salaryRange") String salaryRange);

	
	// filter 평균 연봉
	@Query(value = "SELECT DISTINCT c.cust_salary " + "FROM customer c "
			+ "JOIN card_reg_info cri ON c.cust_id = cri.cust_id " + "JOIN payments p ON cri.reg_id = p.reg_id "
			+ "JOIN job_list j ON c.job_id = j.job_id " + "WHERE c.cust_gender IN (:gender) "
			+ "AND FLOOR((YEAR(CURRENT_DATE) - YEAR(c.cust_birth))/10) IN (:ageRange) "
			+ "AND c.cust_salary = :salaryRange " + "AND j.job_type = :jobType", nativeQuery = true)
	List<String> findDistinctSalaryByFilters(@Param("gender") List<String> gender,
			@Param("ageRange") List<Integer> ageRange, @Param("jobType") String jobType,
			@Param("salaryRange") String salaryRange);
}

