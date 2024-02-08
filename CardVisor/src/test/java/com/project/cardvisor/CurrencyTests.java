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
import com.project.cardvisor.repo.CurrencyRepository;
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
	
	//@Test x
	void f2() {
		 LocalDate currentDate = LocalDate.now();

	        // Calculate the date 3 years ago
	        LocalDate threeYearsAgo = currentDate.minusYears(3);

	        // Loop through the days from 3 years ago until the current date
	        LocalDate date = threeYearsAgo;
	        
	        while (date.isBefore(currentDate) || date.isEqual(currentDate)) {
	           
	            date = date.plusDays(1);
	        }
	}
	
	@Test
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
   	LocalDate startdate = LocalDate.of(2024, 2, 7);

     // Loop through the days from 3 years ago until the current date
 
     
     while (startdate.isBefore(currentDate) || startdate.isEqual(currentDate)) {
    	 System.out.println(startdate);
   
        try {
            // Request URL
            URL url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authkey + "&searchdate=" + startdate + "&data=" + data);
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

                    JSONArray exchangeRateInfoList = (JSONArray) parser.parse(line);

                    for (Object o : exchangeRateInfoList) {
                        JSONObject exchangeRateInfo = (JSONObject) o;
                        String currency_code = (String)exchangeRateInfo.get("cur_unit");
                        String currency_rate1 = (String)exchangeRateInfo.get("deal_bas_r");
                        String valueWithoutCommas = currency_rate1.replaceAll(",", "");
                        Double currency_rate = Double.parseDouble(valueWithoutCommas);
                        String currency_nation = (String)exchangeRateInfo.get("cur_nm");
                        LocalDateTime localDateTime = startdate.atStartOfDay();
                        Timestamp timestamp = Timestamp.valueOf(localDateTime);
                        System.out.println(timestamp);
						
						  CurrencyVO currency = CurrencyVO.builder()
						  .currency_code(currency_code) .currency_nation(currency_nation)
						  .currency_date(timestamp)
						  .currency_rate(currency_rate) .build();
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
        startdate = startdate.plusDays(1);
       }
        if (exchangeRate == null) {
            exchangeRate = defaultExchangeRate;
        }
        
        log.info(result.toString());
	}
}
