package com.project.cardvisor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.repo.JobRepository;
import com.project.cardvisor.vo.CustomerVO;
import com.project.cardvisor.vo.JobListVO;

@SpringBootTest
public class CustomerTest {

	@Autowired
	CustomerRepository crepo;

	@Autowired
	JobRepository jrepo;

	List<String> nameList = new ArrayList<String>();
	List<String> phoneList = new ArrayList<String>();
	List<String> emailList = new ArrayList<String>();


	// 성별 정보 배열
	char[] gender = { '남', '여' };

	// 이메일 도메인 정보 배열
	String[] emailDomain = { "@naver.com", "@gmail.com", "@daum.net", "@hanmail.net" };

	UUID uuid;

	// 급여 정보 배열
	String[] salaryList = { "3000만원 미만", "3000만원 이상 5000만원 미만", "5000만원 이상 7000만원 미만", "7000만원 이상 1억 미만", "1억 이상" };
	String [] salaryList1 = { "3000만원 미만", "3000만원 이상 5000만원 미만"  };
	// 랜덤 생년월일 생성 조정 함수
	static Date getRandomDate(Date start, Date end) {
		return new Date(getRandomTime(start.getTime(), end.getTime()));
	}

	static long getRandomTime(long startMillis, long endMillis) {
		return ThreadLocalRandom.current().nextLong(startMillis, endMillis);
	}

	//@Test
	void f2() {
Random random = new Random();

String[] lastNameOptions = {"방","김", "이", "박", "최", "정", "강", "조", "윤", "장", "임","한","신","류","문","송","배","고","남","손","남궁","황보"};
String[] firstMameOptions = {"민", "영", "희", "성", "덕", "은", "기","원", "용", "종","환","호" ,"신", "철","옥","우","정","석","준","현",
		"선", "온", "남", "형", "두", "태", "혜", "순", "규", "준", "현", "태", "무", "실", "홍", "혁", "훈", "운", "휘", "찬", "은", "진", "석", "덕", "구", "봉", "효", "세", "웅","학",
		"지","민", "서", "진", "영", "재", "수", "현", "상", "경", "래","동", "기","승", "원","훈", "수", "상", "형", "은", "미", "기", "혜", "승", "경"
		, "수", "환", "진","화", "애"};
String[] firstMameOptions1 = {"민", "영", "희", "성", "덕", "은", "기","원", "용", "종","환","호" ,"신", "철","옥","우","정","석","준","현",
		"선", "온", "남", "형", "두", "태", "혜", "순", "규", "준", "현", "태", "무", "실", "홍", "혁", "훈", "운", "휘", "찬", "은", "진", "석", "덕", "구", "봉", "효", "세", "웅","학",
		"지","민", "서", "진", "영", "재", "수", "현", "상", "경", "래","동", "기","승", "원","훈", "수", "상", "형", "은", "미", "기", "혜", "승", "경"
		, "수", "환", "진","화", "애"};
for (int i = 0; i < 100; i++) {
String lastName = getRandomElement(lastNameOptions);
String firstName = getRandomElement(firstMameOptions);
String secondNmae = getRandomElement(firstMameOptions1);
String koreanName =lastName+firstName+secondNmae;
System.out.println(koreanName);
int randomNumber1 = random.nextInt(90000000) + 10000000;
System.out.println(randomNumber1);
}
//        // Generate a random number between 6 and 9 for the number of letters
//        int numLetters = random.nextInt(4) + 6;
//        StringBuilder randomString = new StringBuilder();
//        
//        // Generate random lowercase English letters
//        for (int i = 0; i < numLetters; i++) {
//            char letter = (char) (random.nextInt(26) + 'a');
//            randomString.append(letter);
//        }
//        
//        // Generate random digits for the remaining 3 characters
//        for (int i = 0; i < 3; i++) {
//            char digit = (char) (random.nextInt(10) + '0');
//            randomString.append(digit);
//        }
        
       
      

	        
	}
	

	
	@Test
	void f1() throws IOException {
		Random random = new Random();
		// 1. 이름 리스트업
		BufferedReader reader = new BufferedReader(new FileReader("c:\\nameList.txt"));
		 String[] lastNameOptions = {"방","김", "이", "박", "최", "정", "강", "조", "윤", "장", "임","한","신","류","문","송","배","고","남","손","남궁","황보","황우","제갈","서"};
		 String[] firstMameOptions = {"민", "영", "희", "성", "덕", "은", "기","원", "용", "종","환","호" ,"신", "철","옥","우","정","석","준","현",
					"선", "온", "남", "형", "두", "태", "혜", "순", "규", "준", "현", "태", "무", "실", "홍", "혁", "훈", "운", "휘", "찬", "은", "진", "석", "덕", "구", "봉", "효", "세", "웅","학",
					"지","민", "서", "진", "영", "재", "수", "현", "상", "경", "래","동", "기","승", "원","훈", "수", "상", "형", "은", "미", "기", "혜", "승", "경"
					, "수", "환", "진","화", "애"};
			String[] firstMameOptions1 = {"민", "영", "희", "성", "덕", "은", "기","원", "용", "종","환","호" ,"신", "철","옥","우","정","석","준","현",
					"선", "온", "남", "형", "두", "태", "혜", "순", "규", "준", "현", "태", "무", "실", "홍", "혁", "훈", "운", "휘", "찬", "은", "진", "석", "덕", "구", "봉", "효", "세", "웅","학",
					"지","민", "서", "진", "영", "재", "수", "현", "상", "경", "래","동", "기","승", "원","훈", "수", "상", "형", "은", "미", "기", "혜", "승", "경"
					, "수", "환", "진","화", "애"};
	        
//	        String name;
//		/*
//		 * while ((name = reader.readLine()) != null) { nameList.add(""); }
//		 */
//
//		// 2. 번호 리스트업
//		BufferedReader reader2 = new BufferedReader(new FileReader("c:\\phoneList.txt"));
//		String phone;
//		/*
//		 * while ((phone = reader2.readLine()) != null) { phoneList.add(phone); }
//		 */
//
//		// 3. 이메일 리스트업
//		BufferedReader reader3 = new BufferedReader(new FileReader("c:\\emailList.txt"));
//		String email;
		/*
		 * while ((email = reader3.readLine()) != null) { emailList.add(email); }
		 */
		 int numLetters = random.nextInt(4) + 6;
	       
	        
		// 2. 나이대 설정
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// 20대 ~ 50대 설정 (1965년생 ~ 2004년생)
		Calendar calendar = Calendar.getInstance();
		calendar.set(1946, Calendar.JANUARY, 1);
		Date start = calendar.getTime();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(2003, Calendar.DECEMBER, 31);
		Date end = calendar2.getTime();

		JobListVO j;

		for (int i = 0; i < 15000; i++) {
			 int randomNumber1 = random.nextInt(90000000) + 10000000;
			uuid = UUID.randomUUID();
			char gen;
			
			  if (i % 2 == 0) { gen = gender[0]; } else { gen = gender[1]; }
			 
		
			 String lastName = getRandomElement(lastNameOptions);
		        String firstName = getRandomElement(firstMameOptions);
		        String secondNmae = getRandomElement(firstMameOptions1);
		        String koreanName =lastName+firstName+secondNmae;
		        
			Date randomDate = getRandomDate(start, end);
			java.sql.Date sqlDate = java.sql.Date.valueOf(dateFormat.format(randomDate));
			 StringBuilder randomString = new StringBuilder();
			for (int p = 0; p < numLetters; p++) {
	            char letter = (char) (random.nextInt(26) + 'a');
	            randomString.append(letter);
	        }
			for (int e = 0; e < 3; e++) {
	            char digit = (char) (random.nextInt(10) + '0');
	            randomString.append(digit);
	        }
			int randomNumber = random.nextInt(9) + 1;
			int randomEmailDomain = random.nextInt(4);
			int randomSalary = random.nextInt(5);
			 String finalString = randomString.toString();
			j = jrepo.findById(randomNumber).orElse(null);

			CustomerVO c1 = CustomerVO.builder().custId("US-" + uuid).custName(koreanName).custGender(gen)
					.custBirth(sqlDate).custEmail(finalString + emailDomain[randomEmailDomain])
					.custPhone("010" +randomNumber1).custSalary(salaryList[randomSalary]).jobId(j).build();
			//System.out.println(c1); 
			System.out.println(i);
			crepo.save(c1)
;		}

		//(1940 ~ 1964년생 설정)
		/*
		 * calendar.set(1940, Calendar.JANUARY, 1); start = calendar.getTime();
		 * 
		 * calendar2.set(1964, Calendar.DECEMBER, 31); end = calendar2.getTime();
		 * 
		 * for (int i = 801; i < 1000; i++) { uuid = UUID.randomUUID(); char gen; if (i
		 * % 2 == 0) { gen = gender[0]; } else { gen = gender[1]; }
		 * 
		 * Date randomDate = getRandomDate(start, end); java.sql.Date sqlDate =
		 * java.sql.Date.valueOf(dateFormat.format(randomDate));
		 * 
		 * 
		 * int randomNumber = random.nextInt(9) + 1; int randomEmailDomain =
		 * random.nextInt(4); int randomSalary = random.nextInt(5);
		 * 
		 * j = jrepo.findById(randomNumber).orElse(null);
		 * 
		 * CustomerVO c1 = CustomerVO.builder().custId("US-" +
		 * uuid).custName(nameList.get(i)).custGender(gen)
		 * .custBirth(sqlDate).custEmail(emailList.get(i) +
		 * emailDomain[randomEmailDomain]) .custPhone("010" +
		 * phoneList.get(i)).custSalary(salaryList[randomSalary]).jobId(j).build();
		 * 
		 * crepo.save(c1); System.out.println(c1); }
		 */
		

	}
	  public static String getRandomElement(String[] array) {
	        Random random = new Random();
	        int randomIndex = random.nextInt(array.length);
	        return array[randomIndex];
	    }
}
