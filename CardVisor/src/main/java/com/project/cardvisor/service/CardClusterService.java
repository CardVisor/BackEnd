package com.project.cardvisor.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.CardListRepository;
import com.project.cardvisor.repo.PaymentRepository;
import com.project.cardvisor.vo.CardListVO;

@Service
public class CardClusterService {

	private final CardListRepository clrepo;
	private final PaymentRepository prepo;

	public CardClusterService(CardListRepository clrepo, PaymentRepository prepo) {
		this.clrepo = clrepo;
		this.prepo = prepo;
	}

	// bohyeon start

	public ResponseEntity<List<CardListVO>> getCardsBySort(String month, String sort) {

		YearMonth yearMonth = YearMonth.parse(month);
		LocalDate date2 = yearMonth.atDay(1);

		if (sort.equals("high")) { // 이용률 높은 순
			List<CardListVO> cardList = (List<CardListVO>) clrepo.getCardsBySortAsc(date2);
			return ResponseEntity.ok(cardList);
		} else { // 이용률 낮은 순
			List<CardListVO> cardList = (List<CardListVO>) clrepo.getCardsBySortDesc(date2);
			return ResponseEntity.ok(cardList);
		}

	}

	public Map<Object, Object> getCardsDetail(String month, String type) {

		YearMonth yearMonth = YearMonth.parse(month);
		LocalDate dateFlag = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();

		LocalDate beforeDate = dateFlag.minusMonths(1);
		LocalDate beforeEndDate = dateFlag.minusDays(1);

		int typeNum = Integer.parseInt(type);

		Map<Object, Object> map = new HashMap<Object, Object>();

		// 총 이용고객
		int custNum = clrepo.getCustNum(typeNum, dateFlag);

		NumberFormat formatter = NumberFormat.getInstance();
		String formattedCustNum = formatter.format(custNum);

		map.put("custNum", formattedCustNum);

		// 전월 대비 이용고객 수 증감
		int before = clrepo.getCustNum(typeNum, beforeDate);
		String formattedBeforeCustNum = formatter.format(before);

		map.put("beforeCustNum", formattedBeforeCustNum);

		int compCustNum = custNum - before;
		map.put("compCustNum", compCustNum);

		// 총 결제금액
		Long totalPayNum = prepo.getTotalPayNum(typeNum, dateFlag, endDate);

		String formattedTotalPayNum = formatter.format(totalPayNum);

		map.put("payTotal", formattedTotalPayNum);

		// 전월 총 결제금액 비교하기
		Long compTotalPay;
		Long beforeTotalPay = prepo.getTotalPayNum(typeNum, beforeDate, beforeEndDate);
		String formatteBeforeTotalPay = formatter.format(beforeTotalPay);

		map.put("beforeTotalPay", formatteBeforeTotalPay);

		compTotalPay = totalPayNum - beforeTotalPay;
		map.put("compTotalPay", compTotalPay);

		double compTotalPayPercentage;
		if (beforeTotalPay != 0) { // 분모가 0이 되는 것을 방지
			compTotalPayPercentage = ((double) totalPayNum - beforeTotalPay) / beforeTotalPay * 100;
			compTotalPayPercentage = Math.round(compTotalPayPercentage * 100.0) / 100.0;
		} else {
			compTotalPayPercentage = 0; // 전월 총액이 0인 경우, 증감률은 0으로 처리
		}
		map.put("compTotalPayPercentage", compTotalPayPercentage);

		// 주 사용 연령층
		// 카드별로 , 레그인포에서 익스파이어 데이트가 현재 선택된 날짜보다 늦은 애들 커스트 아이디를 골라 ,
		// 그 커스트 아이디를 가지고 , 고객 테이블로 가서 생년월일로 연령대를 구분을해 , 해서 제일 카운트가 많은 집단 1위를 가지고와.
		String majorAge = clrepo.getMajorAge(typeNum, dateFlag);
		map.put("majorAge", majorAge);

		List<Map<Character, Object>> genderPercentageList = clrepo.getGenderPercentage(typeNum, dateFlag);

		for (Map<Character, Object> row : genderPercentageList) {
			Character gender = (Character) row.get("cust_gender");
			BigDecimal percentage = (BigDecimal) row.get("percentage");
			map.put(gender, percentage);
		}

		Long maleCnt = clrepo.getMaleCnt(typeNum, dateFlag);
		String formattedMaleCnt = formatter.format(maleCnt);
		map.put("maleCnt", formattedMaleCnt);

		Long femaleCnt = clrepo.getFemaleCnt(typeNum, dateFlag);
		String formattedFemaleCnt = formatter.format(femaleCnt);
		map.put("femaleCnt", formattedFemaleCnt);

		List<String> Benefits = clrepo.getBenefitList(typeNum);
		map.put("benefits", Benefits);

		return map;
	}

	public List<Map<String, Object>> getMccChart(String month, String type) {

		YearMonth yearMonth = YearMonth.parse(month);
		LocalDate dateFlag = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();

		int typeNum = Integer.parseInt(type);

		List<Map<String, Object>> mccTopList = clrepo.getMccTopList(typeNum, dateFlag, endDate);

		return mccTopList;
	}

	public Map<String, Object> getMccAllChart(String month, String type) {

		YearMonth yearMonth = YearMonth.parse(month);
		LocalDate dateFlag = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();

		int typeNum = Integer.parseInt(type);

		List<Map<String, Object>> mccAllList = clrepo.getMccAllList(typeNum, dateFlag, endDate);

		Map<String, Object> newMap = new HashMap<>();

		for (Map<String, Object> map : mccAllList) {
			String mccCode = (String) map.get("ctg_name"); // mcc_code를 가져옴
			mccCode = mccCode.replace("/", "");
			BigDecimal amount = (BigDecimal) map.get("total"); // 금액을 가져옴
			newMap.put(mccCode, amount); // 새로운 맵에 저장
		}

		return newMap;
	}

	// bohyeon end

	public List<Map<String, Object>> SelectTop5CardList() {
		List<Map<String, Object>> top5 = clrepo.selectTop5CardList();
		return top5;
	}

}
