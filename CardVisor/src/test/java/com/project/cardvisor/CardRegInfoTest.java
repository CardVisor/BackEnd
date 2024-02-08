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
import java.util.Calendar;
import java.util.List;
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

	static Date getRandomDate(Date start, Date end) {
		return new Date(getRandomTime(start.getTime(), end.getTime()));
	}

	static long getRandomTime(long startMillis, long endMillis) {
		return ThreadLocalRandom.current().nextLong(startMillis, endMillis);
	}

	@Test
	void f2() {
		int count = cservice.addOnedaycustomer();
		System.out.println(count);
	}
	
	//@Test
	void f1() throws IOException {

		BufferedReader reader = new BufferedReader(
				new FileReader("C:\\Users\\User\\Desktop\\DataList\\CustIdList.txt"));

		String custId;

		while ((custId = reader.readLine()) != null) {
			CustIdList.add(custId);
		} // custId

		BufferedReader reader2 = new BufferedReader(
				new FileReader("C:\\Users\\User\\Desktop\\DataList\\CardNumList.txt"));

		String cardNum;

		while ((cardNum = reader2.readLine()) != null) {
			CardNumList.add(cardNum);
		} // custId

		CustomerVO c1;

		// 랜덤 생년월일 생성 조정 함수

		// 3000:card_num=> 순차
		// 60:cardtype
		// 999:custid => 무제한(3)
		// custid -> 999 / card num -> 1~999 / cardtype : 60개 숫자중 랜덤 숫자.
		// 이거 세번 돌기
		// 생년월일 + 19 < reg_date <= 오늘
		// expire = reg_date +5


		for (int i = 0; i < 3000; i++) {
			
			UUID uuid = UUID.randomUUID();
			c1 = crepo.findById(CustIdList.get(i%999)).orElse(null); // 999명의 고객
			Random random = new Random();
			int randomNumber = random.nextInt(62) + 1;
			CardListVO vo = clrepo.findById(randomNumber).orElse(null);;
			
			Date cal = c1.getCust_birth();
			
			
			
			
			LocalDate birthDate = LocalDate.parse(cal.toString());
			LocalDate startDate = birthDate.plusYears(19);
			LocalDate today = LocalDate.now();

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
			
			
			CardRegInfoVO criVO = CardRegInfoVO.builder()
					.reg_id("REG-" + uuid) // uuid 생성 겹칠일 없음
					.card_type(vo) // 카드 번호 3개중에 랜덤으로 ?
					.card_num(CardNumList.get(i)) //카드번호 리스트에서 순차적으로 가져옴
					.reg_date(sqlDate) //
					.expire_date(sqlDate2) // regdate + 5년후
					.cust_id(c1) // 고객번호에서 순차적으로 가져옴
					.build();
			
			crirepo.save(criVO);
		}

	}
}
