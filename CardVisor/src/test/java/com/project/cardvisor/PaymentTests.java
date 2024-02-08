package com.project.cardvisor;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.BenefitRepository;
import com.project.cardvisor.repo.CardBenefitRepository;
import com.project.cardvisor.repo.CardListRepository;
import com.project.cardvisor.repo.CardRegRepository;
import com.project.cardvisor.repo.CurrencyRepository;
import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.repo.MccCodeRepository;
import com.project.cardvisor.repo.PaymentRepository;
import com.project.cardvisor.vo.BenefitVO;
import com.project.cardvisor.vo.CardRegInfoVO;
import com.project.cardvisor.vo.CurrencyVO;
import com.project.cardvisor.vo.MccVO;
import com.project.cardvisor.vo.PaymentsVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PaymentTests {
	
	@Autowired
	PaymentRepository prep;
	
	@Autowired
	CardRegRepository crep;
	
	@Autowired
	MccCodeRepository mrep;
	
	@Autowired
	CustomerRepository custrep;
	
	@Autowired
	CardBenefitRepository cbrep;
	
	@Autowired
	CardListRepository clrep;
	
	@Autowired
	BenefitRepository brep;
	
	@Autowired
	CurrencyRepository currrep;
	
	@Test
	public void f1curr() {
		
		//고객 리스트
		LinkedList<CardRegInfoVO> clist = new LinkedList<>();
		
		crep.findAll().forEach(c -> {
			clist.add(c);
		});
		
		//mcc 코드 타입
		String[] mccCode = {
				"0001", "0002", "0003", "0004", "0005","0006", "0007", "0008", "0009", "0010", "0011",
				"0012", "0013", "0014", "0015","0016"
		};
		String[] 업종 = { "음식점", "편의점", "교통비", "쇼핑몰", "미용실", "병원", "숙박시설", "오락시설", "교육비", "카페", "주거/통신",
				"편의점", "레저/테마", "술/유흥", "국세납입","기타" };
	
		//배열 국가
		String[] nationCode = {"ARE", "AUS", "BHR", "BRN", "CAN", "CHE", 
				"CHN", "DNK", "GBR", "HKG", "IDN", "JPN", "KOR", "KWT",
				"MYS", "NOR", "NZL", "SAU", "SWE", "SGP", "THA", "USA" };//22
		
		//유럽 국가
		String[] europe = { "AUT", "BEL", "CZE", "DNK", "FIN", "FRA", "DEU"
				, "IRL", "ITA", "NLD", "NOR", "POL", "PRT", "SVK", "ESP", "SWE"
				, "CHE", "GBR"
		};//18

		//배열 currencycod
		String[] currencyCode = {"AED", "AUD", "BHD", "BND", "CAD", "CHF", "CNH"
				, "DKK", "GBP", "HKD", "IDR", "JPY", "KRW", "KWD", "MYR", "NOK",
				"NZD", "SAR", "SEK", "SGD", "THB", "USD", "EUR" };//23
		
		Random random = new Random();
		
		for(int i=0; i<10; i++) { //34000
			
			UUID uuid = UUID.randomUUID();
			int mccidx = random.nextInt(16); // mcc 16개
			int regidx = random.nextInt(clist.size()); //999개
			
			int amountidx = random.nextInt(1000); //곱할 숫자

			String nation = "";
			 
			int curridx = random.nextInt(23);	//곱할 환율코드 0~22
			if(currencyCode[curridx].equals("EUR")) {
				int europeidx = random.nextInt(18);	//곱할 환율코드 0~17
				nation = europe[europeidx];
			}else {
				nation = nationCode[curridx];
			}
			
			MccVO mvo = mrep.findById(mccCode[mccidx]).orElse(null);
			
			Date regDate = clist.get(regidx).getReg_date(); // 등록일
		    Date expDate = clist.get(regidx).getExpire_date(); // 만기일
		    java.util.Date randomDate = getRandomDate(regDate, expDate);
		    if (randomDate != null) { // randomDate가 null이 아닌 경우에만 save
		    	
		    	System.out.println("i=" + i);

		    	Timestamp timestamp = new Timestamp(randomDate.getTime());
		    	Timestamp timestampWorkDay = new Timestamp(randomDate.getTime());
		    	
		    	if(randomDate.getDay()==0) {
		    		//토
		    		timestampWorkDay.setDate(randomDate.getDate() - 1) ;
		    	}else if(randomDate.getDay()==6) {
		    		//일
		    		timestampWorkDay.setDate(randomDate.getDate() - 2) ;
		    	}  
		    	
		    	String formattedDate = getFormattedDate(timestampWorkDay)+ " 00:00:00.000";
		    	timestampWorkDay = Timestamp.valueOf(formattedDate);
		    	List<CurrencyVO> currencyList = currrep.findByCurrency_date(timestampWorkDay);
		    	if(currencyList.size()==0) continue;
		    	CurrencyVO currency =  currencyList.stream().filter(curr->curr.getCurrency_code().equals(currencyCode[curridx])).findFirst().get();
		    	
		    	System.out.println("currency:" + currency);
		    	if (currency != null) {
		    	    //double currencyRate = currency.getCurrencyRate();
		    	    PaymentsVO vo = PaymentsVO.builder()
		    	        .pay_id("PA-" + uuid)
		    	        .reg_id(clist.get(regidx))
		    	        .nation(nation)
		    	        .currency_code(currencyCode[curridx])		    	        
		    	        .currency_rate(currency.getCurrency_rate())
		    	        .pay_amount(amountidx * 100)
		    	        .pay_date(timestamp)
		    	        .pay_store(업종[mccidx])
		    	        .mcc_code(mvo)
		    	        .build();
		    	    System.out.println("vo:" + vo);
		    	    prep.save(vo);
		    	}

		    }
		}
	}
	
	
	public String getFormattedDate(Timestamp timestamp) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date(timestamp.getTime());
	    return dateFormat.format(date);
	}
	
	public void f1() {
		
		//고객 리스트
		LinkedList<CardRegInfoVO> clist = new LinkedList<>();
		crep.findAll().forEach(c -> {
			clist.add(c);
		});
		//mcc 코드 타입
		/*
		String[] mccCode = {
				"0001", "0002", "0003", "0004", "0005","0006", "0007", "0008", "0009", "0010", "0011",
				"0012", "0013", "0014", "0015","0016"
		};
		String[] 업종 = { "음식점", "편의점", "교통비", "쇼핑몰", "미용실", "병원", "숙박시설", "오락시설", "교육비", "카페", "주거/통신",
				"편의점", "레저/테마", "술/유흥", "국세납입","기타" };
		*/
		
		String[] mccCode2 = {
				"0001", "0002", "0004", "0010","0012"};
		String[] 업종2 = { "음식점", "생활시설", "쇼핑몰", "카페",
				"편의점"};

		Random random = new Random();
		
		for(int i=0; i<10000; i++) { //34000
			UUID uuid = UUID.randomUUID();
			//int mccidx = random.nextInt(16); // mcc 16개
			int mccidx2 = random.nextInt(5); // mcc 16개
			
			int regidx = random.nextInt(clist.size()); //999개
			int amountidx = random.nextInt(100); //곱할 숫자
			MccVO mvo = mrep.findById(mccCode2[mccidx2]).orElse(null);
			
			Date regDate = clist.get(regidx).getReg_date(); // 등록일
		    Date expDate = clist.get(regidx).getExpire_date(); // 만기일
		    java.util.Date randomDate = getRandomDate(regDate, expDate);
		    if (randomDate != null) { // randomDate가 null이 아닌 경우에만 save
		        Timestamp timestamp = new Timestamp(randomDate.getTime());
				
				PaymentsVO vo = PaymentsVO.builder()
						.pay_id("PA-"+uuid)
						.reg_id(clist.get(regidx))
						.nation("KOR")
						.currency_code("KRW")
						.currency_rate(1)
						.pay_amount(amountidx*1000)
						.pay_date(timestamp)
						.pay_store(업종2[mccidx2])
						.mcc_code(mvo)
						.build();
				prep.save(vo);
		    }
		}
	}
	
	@Test
	public void f2() {
		prep.findAll().forEach(p -> {
			
			if(p.getBenefit_amount() > 0) {
				return;
			}
			MccVO curMcc = p.getMcc_code();
			
			List<BenefitVO> bvo = brep.findByPay_id(p.getPay_id());
			
			bvo.forEach(b -> {
				if(b.getMcc_code().equals(curMcc.getMcc_code())) {
					p.setBenefit_amount((int)Math.floor(p.getPay_amount()*b.getBenefit_pct()));
					prep.save(p);
				}
			});
		});
	}
	
	public static java.util.Date getRandomDate(java.util.Date start, java.util.Date end) {
	    java.util.Date now = new java.util.Date(); // 현재 시간

	    // end가 현재 시간보다 이후이면, end를 현재 시간으로 설정
	    if (end.after(now)) {
	        end = now;
	        if(end.after(start)) {
	        	
	        } else {
	        	return null;
	        }
	    }

	    // start와 end 사이에서 랜덤 날짜 생성
	    long startMillis = start.getTime();
	    long endMillis = end.getTime();
	    long randomMillisSinceEpoch = ThreadLocalRandom
	        .current()
	        .nextLong(startMillis, endMillis);

	    java.util.Date randomDate = new java.util.Date(randomMillisSinceEpoch);

	    // 시간, 분, 초 랜덤 설정
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(randomDate);
	    cal.set(Calendar.HOUR_OF_DAY, ThreadLocalRandom.current().nextInt(24)); // 시간
	    cal.set(Calendar.MINUTE, ThreadLocalRandom.current().nextInt(60)); // 분
	    cal.set(Calendar.SECOND, ThreadLocalRandom.current().nextInt(60)); // 초

	    // 생성된 랜덤 날짜가 현재로부터 4년 이내인지 확인
	    cal = Calendar.getInstance();
	    cal.setTime(now);
	    cal.add(Calendar.YEAR, -3); // 3년 전
	    java.util.Date fourYearsAgo = cal.getTime(); // 현재로부터 4년 전
	    if (randomDate.before(fourYearsAgo)) {
	        return null; // 생성된 랜덤 날짜가 현재로부터 4년 이전이면, null 반환
	    }

	    // 생성된 랜덤 날짜 반환
	    return randomDate;
	}
	
	
	
	


	
	//카드 분류 타입으로
//	모든 사람 랜덤 카드 : 35000건
//	 * 딥드림 카드 : 10000건 26, 27, 28
//	 * 
//	 * 여행 3000건 interestid => 6
//	 * 2000건

	
}
