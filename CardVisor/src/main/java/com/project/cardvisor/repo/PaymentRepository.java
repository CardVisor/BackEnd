package com.project.cardvisor.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.cardvisor.vo.PaymentsVO;

public interface PaymentRepository extends CrudRepository<PaymentsVO, String>{

	@Query(value="SELECT sum(pay_amount) "
			+ "FROM payments "
			+ "WHERE currency_code = 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 0 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00')  "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 0 MONTH , '%Y-%m-%d %H:%i:%s')",nativeQuery = true)
	 Long TotalAmountPayments();
	
	
	@Query(value="SELECT sum(pay_amount) "
			+ "FROM payments "
			+ "WHERE currency_code = 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 1 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00') "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 1 MONTH , '%Y-%m-%d %H:%i:%s')",nativeQuery = true)
	 Long LastMonthTotalAmountPayments();
	
	@Query(value="SELECT sum(pay_amount) "
			+ "FROM payments "
			+ "WHERE currency_code != 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 0 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00')  "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 0 MONTH , '%Y-%m-%d %H:%i:%s')",nativeQuery = true)
	 Long AbroadTotalAmountPayments();
	
	
	@Query(value="SELECT sum(pay_amount) "
			+ "FROM payments "
			+ "WHERE currency_code != 'KRW' and pay_date >= DATE_FORMAT(NOW() - INTERVAL 1 MONTH + INTERVAL 1 DAY, '%Y-%m-01 00:00:00') "
			+ "AND pay_date <= DATE_FORMAT(NOW() - INTERVAL 1 MONTH , '%Y-%m-%d %H:%i:%s')",nativeQuery = true)
	 Long AbroadLastMonthTotalAmountPayments();
	
	
	/////// 은경 ///////
	//올해 해외 토탈 결제 금액
	@Query("SELECT SUM(p.pay_amount)"
			+ " FROM PaymentsVO p"
			+ " WHERE p.currency_code != 'KRW'"
			+ " AND YEAR(p.pay_date) = YEAR(CURRENT_DATE)")
	Long selectTotalOverseasPayment();
	
	//전년 월 대비 올해 월 증감 (+/-)
	@Query(value = "select thisYear.currentSum - lastYear.lastSum"
				+ " from"
				+ " ("
					+ " SELECT SUM(p.pay_amount) currentSum"
					+ " FROM payments p"
					+ " WHERE p.CURRENCY_CODE != 'KRW'"
					+ " AND YEAR(p.pay_date) = YEAR(CURRENT_DATE)"
					+ " AND MONTH(p.pay_date) = :month"
				+ " ) thisYear,"
				+ " ("
					+ " SELECT SUM(p.pay_amount) lastSum"
					+ " FROM payments p"
					+ " WHERE p.CURRENCY_CODE != 'KRW'"
					+ " AND YEAR(p.pay_date) = YEAR(CURRENT_DATE) - 1"
					+ " AND MONTH(p.pay_date) = :month"
				+ " ) lastYear", nativeQuery = true)
	Long selectDiffPaymentThisYearAndLastYear(@Param("month") int month);

	//올해 건수가 제일 많은 나라 (순위 리스트업)
	@Query(value ="SELECT COUNT(P.CURRENCY_CODE), P.CURRENCY_CODE, C.CURRENCY_NATION"
			+ " FROM PAYMENTS P"
			+ " JOIN CURRENCY C ON (P.CURRENCY_CODE = C.CURRENCY_CODE)"
			+ " WHERE P.CURRENCY_CODE != 'KRW'"
			+ " AND YEAR(P.PAY_DATE) = YEAR(CURRENT_DATE)"
			+ " GROUP BY P.CURRENCY_CODE, C.CURRENCY_NATION"
			+ " ORDER BY COUNT(P.CURRENCY_CODE) DESC", nativeQuery = true)
	List<Map<String, Object>> selectHighestOrderPayment();
	
}
