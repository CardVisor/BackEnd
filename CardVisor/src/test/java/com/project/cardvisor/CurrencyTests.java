package com.project.cardvisor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cardvisor.repo.CardListRepository;
import com.project.cardvisor.repo.CurrencyRepository;
import com.project.cardvisor.service.CurrencyService;
import com.project.cardvisor.vo.CurrencyVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class CurrencyTests {
	
	@Autowired
	CurrencyRepository crepo;
	@Value("${exchange-authkey}")
	String authkey;
	@Value("${exchange-data}")
    String data ;
	
	@Autowired
	CurrencyService cService;
	
	@Autowired
	CardListRepository clrepo;
	@Test
	void f4() {
		List<Map<String, Object>> top5 = clrepo.selectTop5CardList();
		for(Map<String, Object> o : top5) {
			for(Map.Entry<String, Object> entry : o.entrySet()) {
	            System.out.println(entry.getKey() + ": " + entry.getValue());
	        }
		}
	}
	
	//@Test
	void f3() {
		List<CurrencyVO>  cList = cService.LatestCurrencyData();
		for(CurrencyVO clist : cList) {
			System.out.println(clist.getCurrencyCode());
			System.out.println(clist.getCurrencyRate());
		}
		
	}
	
	
	//@Test 
	void f2() {
		 LocalDate currentDate = LocalDate.now();

	        // Calculate the date 3 years ago
	        LocalDate threeYearsAgo = currentDate.minusYears(3);

	        // Loop through the days from 3 years ago until the current date
	        LocalDate date = threeYearsAgo;
	        
	        while (date.isBefore(currentDate) || date.isEqual(currentDate)) {
	           System.out.println(date);
	            date = date.plusDays(1);
	        }
	}
	
	//@Test
	void f1() {
		
ObjectMapper objectmapper = new ObjectMapper();

		
	    HttpURLConnection connection = null;
	    BigDecimal defaultExchangeRate = BigDecimal.valueOf(1300);
	    
		BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        JSONParser parser = new JSONParser();
        //2월1일 검색
        //Date date = new Date(124, 1, 1);
        //String searchDate = new SimpleDateFormat("yyyyMMdd").format(date);
        //String searchDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        
        BigDecimal exchangeRate = null;
        List<Object> result = new ArrayList<>();
   	 LocalDate currentDate = LocalDate.now();

     // Calculate the date 3 years ago
   	LocalDate startdate = LocalDate.of(2022, 2, 14);
    //LocalDate threeYearsAgo = currentDate.minusYears(3);
   	LocalDate threeYearsAgo = LocalDate.of(2021, 1, 1);
     // Loop through the days from 3 years ago until the current date
    LocalDate date =threeYearsAgo;
    String currency_flagUrl = null ;
     while (date.isBefore(startdate) || startdate.isEqual(startdate)) {
    	 
   
        try {
            // Request URL
            URL url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authkey + "&searchdate=" + date + "&data=" + data);
            connection = (HttpURLConnection) url.openConnection();
           
            // Request 초기 세팅
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            // API 호출
            // 실패했을 경우 Connection Close
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    System.out.println("실패라고?");
                }
                reader.close();
            } else { // 성공했을 경우 환율 정보 추출
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                	System.out.println(startdate);
                    JSONArray exchangeRateInfoList = (JSONArray) parser.parse(line);

                    for (Object o : exchangeRateInfoList) {
                        JSONObject exchangeRateInfo = (JSONObject) o;
                        String currency_code = (String)exchangeRateInfo.get("cur_unit");
                        String currency_rate1 = (String)exchangeRateInfo.get("deal_bas_r");
                        String valueWithoutCommas = currency_rate1.replaceAll(",", "");
                        Double currency_rate = Double.parseDouble(valueWithoutCommas);
                        String currency_nation = (String)exchangeRateInfo.get("cur_nm");
                        
                        LocalDateTime localDateTime = date.atStartOfDay();
                        Timestamp timestamp = Timestamp.valueOf(localDateTime);
                        System.out.println(timestamp);
						switch (currency_code) {
						case "USD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/usd.svg";
							break;
						case "THB":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/thb.svg";
							break;
						case "SGD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/sgd.svg";
							break;
						case "SEK":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/sek.svg";
							break;
						case "SAR":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/sar.png";
						break;
						case "NZD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/NZD.png";
							break;
						case "NOK":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/NOK.png";
							break;
						case "MYR":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/MYR.png";
							break;
						case "KWD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/KWD.png";
							break;
						case "KRW":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/KRW.png";
							break;
						case "JPY(100)":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/JPY.png";
							break;
						case "IDR(100)":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/IDR.png";
							break;
						case "HKD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/HKD.png";
							break;
						case "GBP":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/GBP.png";
							break;
						case "EUR":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/EUR.png";
							break;
						case "DKK":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/DKK.png";
							break;
						case "CNH":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/CNH.png";
							break;
						case "CAD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/CAD.png";
							break;
						case "BND":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/BND.png";
							break;
						case "BHD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/BHD.png";
							break;
						case "AUD":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/AUD.png";
							break;
						case "AED":
							currency_flagUrl="https://farmfarmimagess.s3.ap-northeast-2.amazonaws.com/currency_img/AED.png";
							break;
							
						default:
							break;
						}
						  CurrencyVO currency = CurrencyVO.builder()
						  .currencyCode(currency_code) .currencyNation(currency_nation)
						  .currencyDate(timestamp)
						  .currencyFlagUrl(currency_flagUrl)
						  .currencyRate(currency_rate) .build();
						 crepo.save(currency);
                            	
                          
                      
                        	
                        
                    }
                }
                reader.close();
            }
            System.out.println(responseContent.toString());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
            
        }//catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
	//	} 
	catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        date = date.plusDays(1);
       }
        if (exchangeRate == null) {
            exchangeRate = defaultExchangeRate;
        }
        
        log.info(result.toString());
	}
}
