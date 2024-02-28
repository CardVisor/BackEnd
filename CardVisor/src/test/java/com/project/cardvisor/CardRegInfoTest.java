package com.project.cardvisor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.CardListRepository;
import com.project.cardvisor.repo.CardRegInfoRepository;
import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.service.CardReginfoService;
import com.project.cardvisor.vo.CardListVO;
import com.project.cardvisor.vo.CardRegInfoVO;
import com.project.cardvisor.vo.CustomerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CardRegInfoTest {

	@Autowired
	CardRegInfoRepository crirepo;

	@Autowired
	CardListRepository clrepo;

	@Autowired
	CustomerRepository crepo;

	@Autowired
	CardReginfoService cservice;
	// 카드 레포 추가해야함

	/*
	 * @Id private String reg_id;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="cust_id") private CustomerVO cust_id;
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="card_type") private CardListVO card_type;
	 * 
	 * private String card_num;
	 * 
	 * @CreationTimestamp private Timestamp reg_date;
	 * 
	 * private Timestamp expire_date;
	 */

	// reg_id -> "REG-" + UUID
	// cust_id -> randomnumber ? 일단 하나씩 다 가져오고 ..

	// card_type -> 1~62 중에 랜덤으로 가져오기
	// card_num -> 리스트에서 하나씩 가져오면됨

	// reg_date

	// 파일에 존재하는 애들 읽어오기
	List<String> CustIdList = new ArrayList<String>(); // 고객명 현재 999건
	List<String> CardNumList = new ArrayList<String>(); // 카드번호 현재 3000건
	int [] cardNumselect = {27,26,9,1,10};
	static Date getRandomDate(Date start, Date end) {
		return new Date(getRandomTime(start.getTime(), end.getTime()));
	}

	static long getRandomTime(long startMillis, long endMillis) {
		return ThreadLocalRandom.current().nextLong(startMillis, endMillis);
	}

	//@Test
	void f4() {
		CustomerVO c2;
		List<String> custList = new ArrayList<>();
		crepo.findAll().forEach(c -> {
		    custList.add(c.getCustId()); // CustomerVO 객체를 리스트에 추가
		});
		CardRegInfoVO checkreg ;
		for (int i=0;i<100;i++) {
		String customerId = custList.get(i);
		
		checkreg =  crirepo.findById(customerId).orElse(null);
		if(checkreg != null && checkreg.getRegId() != null && checkreg.getRegId().length() > 2) {
			return;
		}
		 c2 = crepo.findById(customerId).orElse(null);
		 if (c2 != null) {
		        Date cdate = c2.getCustBirth();
		        System.out.println(cdate);
		    }
		}
		}
	
	
	//@Test
	void f3() {
		List<Integer> cclist = new ArrayList<Integer>();
		clrepo.findAll().forEach(c -> {
		    cclist.add(c.getCardType()); // Add card type to the list
		    
		});
int cc = cclist.get(1);
System.out.println(cc);
		// Ensure that cclist has at least 2 elements before accessing them
	


	}
	//@Test
	void f2() {
	     // Generate and print 10 random card numbers
        for (int i = 0; i < 10; i++) {
            String cardNumber = generateCardNumber();
            System.out.println("Random Card Number " + (i + 1) + ": " + cardNumber);
        }
	}
	
@Test
	void f1() throws IOException {

//		BufferedReader reader = new BufferedReader(
//				new FileReader("C:\\Users\\User\\Desktop\\DataList\\CustIdList.txt"));

//		String custId;
//
//		while ((custId = reader.readLine()) != null) {
//			CustIdList.add(custId);
//		} // custId
//
//		BufferedReader reader2 = new BufferedReader(
//				new FileReader("C:\\Users\\User\\Desktop\\DataList\\CardNumList.txt"));
//
//		String cardNum;
		List<Integer> cclist = new ArrayList<Integer>();
		clrepo.findAll().forEach(c -> {
		    cclist.add(c.getCardType()); // Add card type to the list
		    
		});

//		while ((cardNum = reader2.readLine()) != null) {
//			CardNumList.add(cardNum);
//		} // custId
		 
		CustomerVO c1;
	
		// 랜덤 생년월일 생성 조정 함수

		// 3000:card_num=> 순차
		// 60:cardtype
		// 999:custid => 무제한(3)
		// custid -> 999 / card num -> 1~999 / cardtype : 60개 숫자중 랜덤 숫자.
		// 이거 세번 돌기
		// 생년월일 + 19 < reg_date <= 오늘
		// expire = reg_date +5
		List<String> custList = new ArrayList<>();
		crepo.findAll().forEach(c -> {
		    custList.add(c.getCustId()); // CustomerVO 객체를 리스트에 추가
		});
		CardRegInfoVO checkreg ;// cardleginfo에 카드 2개이상 있는지 체크를 위해 
		for (int i = 1; i < 20000; i++) {
			
			UUID uuid = UUID.randomUUID();
			//c1 = crepo.findById(CustIdList.get(i%999)).orElse(null); // 999명의 고객
		String customerId = custList.get(i%29990);//29990의 고객
		c1 = crepo.findById(customerId).orElse(null);
		checkreg =  crirepo.findById(customerId).orElse(null);
		//cardreginfo에 regid가 2개이상이면 다시 return
		if(checkreg != null && checkreg.getRegId() != null && checkreg.getRegId().length() > 2) { 
			
			return;
		}
		 //c2 = crepo.findById(customerId).orElse(null);
//		 if (c2 != null) {
//		       cdate = c2.getCustBirth();
//		       
//		    }
			Random random = new Random();
			int randomNumber = random.nextInt(62) + 1;
			int randomSalary = random.nextInt(5);
			CardListVO vo = clrepo.findById(randomNumber).orElse(null);
			//int cardtype = cclist.get(randomNumber);
			//Date cal = c1.getCustBirth();
			
	
			
			
			//LocalDate birthDate = LocalDate.parse(cdate.toString());
			//LocalDate startDate = birthDate.plusYears(19);
			LocalDate today = LocalDate.now();
			LocalDate startDate = today.minusYears(3);

			long daysBetween = ChronoUnit.DAYS.between(startDate, today);
			long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

			LocalDate randomDate = startDate.plusDays(randomDays);

			Date date = java.sql.Date.valueOf(randomDate);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date sqlDate = java.sql.Date.valueOf(format.format(date));
			

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, 5);
			Date datePlusFiveYears = new Date(calendar.getTimeInMillis());
			java.sql.Date sqlDate2 = java.sql.Date.valueOf(format.format(datePlusFiveYears));
			
			String cardNumber = generateCardNumber(); //카드번호 자동생성
			CardRegInfoVO criVO = CardRegInfoVO.builder()
					.regId("REG-" + uuid) // uuid 생성 겹칠일 없음
					.cardType(vo) // 카드 번호 3개중에 랜덤으로 ?
					.cardNum(cardNumber) //카드번호 리스트에서 순차적으로 가져옴
					.regDate(sqlDate) //
					.expireDate(sqlDate2) // regdate + 5년후
					.custId(c1) // 고객번호에서 순차적으로 가져옴
					.build();
			
			crirepo.save(criVO);
			System.out.println(criVO);
		}

	}
	//자동 카드번호 추출기
	public static String generateCardNumber() {
        StringBuilder cardNumberBuilder = new StringBuilder();
        Random random = new Random();

        // Generate 16 random digits
        for (int i = 0; i < 16; i++) {
            // Insert hyphens at appropriate positions
            if (i > 0 && i % 4 == 0) {
                cardNumberBuilder.append("-");
            }
            cardNumberBuilder.append(random.nextInt(10)); // Append a random digit from 0 to 9
        }

        return cardNumberBuilder.toString();
    }

}
