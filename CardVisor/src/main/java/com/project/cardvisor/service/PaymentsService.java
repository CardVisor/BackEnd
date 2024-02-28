package com.project.cardvisor.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.PaymentRepository;
import com.project.cardvisor.vo.PaymentsVO;

@Service
public class PaymentsService {

	@Autowired
	PaymentRepository payrepo;
	
	public Long TotalAmountPayments() {
		
		Long amount = payrepo.TotalAmountPayments();
		return amount;
	}
public Long LastMonthTotalAmountPayments() {
		
	Long amount1 = payrepo.TotalAmountPayments();
	Long amount = payrepo.LastMonthTotalAmountPayments(); 
	Long totalamount= amount1- amount;
	System.out.println("total:"+totalamount);
		return totalamount;
	}
public Long AbroadTotalAmountPayments() {
	Long amount =payrepo.AbroadTotalAmountPayments();
	return amount;
}

public Long AbroadLastMonthTotalAmountPayments() {
	Long amount = payrepo.AbroadLastMonthTotalAmountPayments();
	if(amount==null) {
		amount=(long) 0;
	}
	Long amount1 = payrepo.AbroadTotalAmountPayments();
	if(amount1==null) {
		amount1=(long) 0;
	}
	Long totalamount= amount1- amount;
	return totalamount;
}
//용수
public List<Map<String, Object>> selectPerMonthamount() {
    List<Map<String, Object>> pList = payrepo.selectPerMonthamount();
    return pList;
}
public List<List<Map<String,Object>>>SelectLastYearAndPerMonthamount() {
	List<Map<String, Object>> LastYearpList = payrepo.selectLastYearPerMonthamount();
	List<Map<String, Object>> yearp1ist = payrepo.selectPerMonthamount();
	
	List<List<Map<String,Object>>> pList = new ArrayList<>();
	pList.add(LastYearpList);
	pList.add(yearp1ist);
	
	
	return pList;
}
public int perMonthTotalAmount() {
	Long totalamount = payrepo.perMonthTotalAmount();
	 String formattedAmount = String.valueOf(totalamount / 1000000);
	       int parsedValue = Integer.parseInt(formattedAmount);
	return parsedValue;
}
public Double TotalIncrese() {
	Long totalamount = payrepo.perMonthTotalAmount();
	Long Lastmonthtotalamount = payrepo.lastYearPerMonthTotalAmount();
	Double TotalIncrese = (((double)(totalamount -Lastmonthtotalamount)/Lastmonthtotalamount)*100);
	 DecimalFormat df = new DecimalFormat("0.0");

     // Format the double value using DecimalFormat
     String formattedValue = df.format(TotalIncrese);
     double parsedValue = Double.parseDouble(formattedValue);
	return parsedValue;
}
public List<Map<String, Object>> SelectLastYearPerMonthamount() {
	List<Map<String, Object>> pList = payrepo.selectLastYearPerMonthamount();
	return pList;
}

//주별..
public List<Map<String, Object>> selectPerWeeklyamount() {
    List<Map<String, Object>> pList = payrepo.selectPerWeeklyamount();
    return pList;
}
public List<Map<String, Object>> SelectLastYearPerWeeklyamount() {
    List<Map<String, Object>> pList = payrepo.selectLastYearPerWeeklyamount();
    return pList;
}
public int perWeekTotalAmount() {
	Long totalamount = payrepo.perWeekTotalAmount();
	 String formattedAmount = String.valueOf(totalamount / 1000000);
	       int parsedValue = Integer.parseInt(formattedAmount);
	return parsedValue;
}
public Double WeekTotalIncrese() {
	Long weektotalamount = payrepo.perWeekTotalAmount();
	Long Lastweektotalamount = payrepo.lastYearPerWeekTotalAmount();
	Double TotalIncrese = (((double)(weektotalamount -Lastweektotalamount)/Lastweektotalamount)*100);
	 DecimalFormat df = new DecimalFormat("0.0");

     // Format the double value using DecimalFormat
     String formattedValue = df.format(TotalIncrese);
     double parsedValue = Double.parseDouble(formattedValue);
	return parsedValue;
}

//월별 거래건수
public List<Map<String, Object>> selectPerMonthtransaction() {
    List<Map<String, Object>> pList = payrepo.selectPerMonthtransaction();
    return pList;
}
	
public int selectMonthtransaction() {
	int amount = payrepo.selectMonthtransaction();
	return amount;
}
//주간 거래건수


public int selectWeektransaction() {
	int amount =payrepo.selectWeektransaction();
	return amount;
}
public List<Map<String, Object>> selectPerWeeklytransaction() {
	  List<Map<String, Object>> pList = payrepo.selectPerWeeklytransaction();
	    return pList;
}
public List<Map<String, Object>> PayAmountTop5Card(){
	 List<Map<String, Object>> plist = payrepo.PayAmountTop5Card();
	 return plist;
}
public List<Map<String, Object>> AbroadPayAmountTop5Card(){
	List<Map<String, Object>> plist = payrepo.AbroadPayAmountTop5Card();
	return plist;
}
public List<Map<String, Object>> benefitTop5Card(){
	List<Map<String, Object>> blist =payrepo.benefitTop5Card();
	return blist;
}


 
//Detail
public List<Map<String, Object>> DetailselectPerMonthamount() {
    List<Map<String, Object>> pList = payrepo.DetailselectPerMonthamount();
    return pList;
}
public int DetailperMonthTotalAmount() {
	Long totalamount = payrepo.DetailperMonthTotalAmount();
	 String formattedAmount = String.valueOf(totalamount / 1000000);
	       int parsedValue = Integer.parseInt(formattedAmount);
	return parsedValue;
}
public Double DetailTotalIncrese() {
	Long totalamount = payrepo.DetailperMonthTotalAmount();
	Long Lastmonthtotalamount = payrepo.DetaillastYearPerMonthTotalAmount();
	Double TotalIncrese = (((double)(totalamount -Lastmonthtotalamount)/Lastmonthtotalamount)*100);
	 DecimalFormat df = new DecimalFormat("0.0");

     // Format the double value using DecimalFormat
     String formattedValue = df.format(TotalIncrese);
     double parsedValue = Double.parseDouble(formattedValue);
	return parsedValue;
}
public List<Map<String, Object>> DetailSelectLastYearPerMonthamount() {
	List<Map<String, Object>> pList = payrepo.DetailselectLastYearPerMonthamount();
	return pList;
}

//주별..
public List<Map<String, Object>> DetailselectPerWeeklyamount() {
    List<Map<String, Object>> pList = payrepo.DetailselectPerWeeklyamount();
    return pList;
}
public List<Map<String, Object>> DetailSelectLastYearPerWeeklyamount() {
    List<Map<String, Object>> pList = payrepo.DetailselectLastYearPerWeeklyamount();
    return pList;
}
public int DetailperWeekTotalAmount() {
	Long totalamount = payrepo.DetailperWeekTotalAmount();
	 String formattedAmount = String.valueOf(totalamount / 1000000);
	       int parsedValue = Integer.parseInt(formattedAmount);
	return parsedValue;
}
public Double DetailWeekTotalIncrese() {
	Long weektotalamount = payrepo.DetailperWeekTotalAmount();
	Long Lastweektotalamount = payrepo.DetaillastYearPerWeekTotalAmount();
	Double TotalIncrese = (((double)(weektotalamount -Lastweektotalamount)/Lastweektotalamount)*100);
	 DecimalFormat df = new DecimalFormat("0.0");

     // Format the double value using DecimalFormat
     String formattedValue = df.format(TotalIncrese);
     double parsedValue = Double.parseDouble(formattedValue);
	return parsedValue;
}

//월별 거래건수
public List<Map<String, Object>> DetailselectPerMonthtransaction() {
    List<Map<String, Object>> pList = payrepo.DetailselectPerMonthtransaction();
    return pList;
}
	
public int DetailselectMonthtransaction() {
	int amount = payrepo.DetailselectMonthtransaction();
	return amount;
}
//주간 거래건수


public int DetailselectWeektransaction() {
	int amount =payrepo.DetailselectWeektransaction();
	return amount;
}
public List<Map<String, Object>> DetailselectPerWeeklytransaction() {
	  List<Map<String, Object>> pList = payrepo.DetailselectPerWeeklytransaction();
	    return pList;
}

public int benefitTotalAmount() {
	int amount = payrepo.benefitTotalAmount();
	return amount;
}
}
