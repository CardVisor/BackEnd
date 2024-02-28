package com.project.cardvisor.controller;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cardvisor.service.CardClusterService;
import com.project.cardvisor.service.CardReginfoService;
import com.project.cardvisor.service.CurrencyService;
import com.project.cardvisor.service.CustClusterService;
import com.project.cardvisor.service.PaymentsService;
import com.project.cardvisor.vo.CurrencyVO;

@RestController
@RequestMapping("/main")
public class MainController {
	@Autowired
	 CustClusterService genderRatioService;

	@Autowired
	CardClusterService cardservice;
	@Autowired
	CardReginfoService cardreginfoservice;
	@Autowired
	PaymentsService paymentservice;
	
	
	@Autowired
	CurrencyService currencyservice;
	
	   //총 회원 수
    @GetMapping("/totalCustomer")
    public int CustomerTotalCount () {
    	int size = genderRatioService.CustomerTotalCount();
    	System.out.println("size:"+size);
    	return size;
    	}
	//매월 1일에 증가된 회원수
    @GetMapping("/addOneDayCustomer")
    public int addOnedaycustomer () {
    	int size = cardreginfoservice.addOnedaycustomer();
    	System.out.println("size:"+size);
    	return size;
    	}
    
    @GetMapping("/totalAmount")
    public Long TotalAmountPayments() {
    	Long amount2 = paymentservice.TotalAmountPayments(); 
        System.out.println("amount2"+amount2);
    	return amount2;
    }
    
    @GetMapping("/lastMonthTotalAmount")
    public Long LastMonthTotalAmountPayments() {
     	Long totalamount = paymentservice.LastMonthTotalAmountPayments();
    System.out.println("totalamount"+totalamount);
    	return totalamount;
    }
    
    @GetMapping("/abroadTotalAmount")
    public Long AbroadTotalAmountPayments() {
    	Long amount2 = paymentservice.AbroadTotalAmountPayments(); 
        System.out.println("Abroadamount2"+amount2);
    	return amount2;
    }
    
    @GetMapping("/abroadLastMonthTotalAmount")
    public Long AbroadLastMonthTotalAmountPayments() {
     	Long totalamount = paymentservice.AbroadLastMonthTotalAmountPayments();
    System.out.println("Abroadtotalamount"+totalamount);
    	return totalamount;
    }
    
    @GetMapping("/latestCurrencyinfo")
    public List<CurrencyVO> LatestCurrencyinfo(){
    	List<CurrencyVO> cList = currencyservice.LatestCurrencyData();
    	return cList;
    }
    //여기서부터 추가
    @GetMapping("/selectPerMonthamount")
    public List<Map<String, Object>> SelectPerMonthamount() {
    	 List<Map<String, Object>> plist = paymentservice.selectPerMonthamount();
    	return plist;
    }
    @GetMapping("/selectLastYearPerMonthamount")
    public List<Map<String, Object>> SelectLastYearPerMonthamount() {
    	 List<Map<String, Object>> plist = paymentservice.SelectLastYearPerMonthamount();
    	return plist;
    }
    @GetMapping("/selectLastYearAndPerMonthamount")
    public List<List<Map<String,Object>>>SelectLastYearAndPerMonthamount() {
    	 List<List<Map<String,Object>>> plist = paymentservice.SelectLastYearAndPerMonthamount();
    	 return plist;
    }
    @GetMapping("/perMonthTotalAmount")
    public int PerMonthTotalAmount() {
    	int totalamount = paymentservice.perMonthTotalAmount();
    	return totalamount;
    }
    @GetMapping("/totalIncrese")
    public Double TotalIncrese() {
    	double totalIncrese = paymentservice.TotalIncrese();
    	return totalIncrese;
    }
    //주별..
    @GetMapping("/selectPerWeeklyamount")
    public List<Map<String, Object>> SelectPerWeeklyamount() {
   	 List<Map<String, Object>> plist = paymentservice.selectPerWeeklyamount();
   	return plist;
   }
    
    @GetMapping("/selectLastYearPerWeeklyamount")
    public List<Map<String, Object>> SelectLastYearPerWeeklyamount() {
   	 List<Map<String, Object>> plist = paymentservice.SelectLastYearPerWeeklyamount();
   	return plist;
   }
    @GetMapping("/perWeekTotalAmount")
    public int perWeekTotalAmount() {
    	int totalamount = paymentservice.perWeekTotalAmount();
    	return totalamount;
    }
    @GetMapping("/weektotalIncrese")
    public Double WeekTotalIncrese() {
    	double totalIncrese = paymentservice.WeekTotalIncrese();
    	return totalIncrese;
    }
    
    //월별 거래건수
    @GetMapping("/selectPerMonthtransaction")
    public List<Map<String, Object>> SelectPerMonthtransaction() {
   	 List<Map<String, Object>> plist = paymentservice.selectPerMonthtransaction();
   	return plist;
   }
    @GetMapping("/selectMonthtransaction")
    public int SelectMonthtransaction() {
    	int amount = paymentservice.selectMonthtransaction();
    	return amount;
    }
    //주간 거래건수
    @GetMapping("/selectPerWeeklytransaction")
    public List<Map<String, Object>> SelectPerWeeklytransaction() {
   	 List<Map<String, Object>> plist = paymentservice.selectPerWeeklytransaction();
   	return plist;
   }
    @GetMapping("/selectWeektransaction")
    public int selectWeektransaction() {
    	int amount = paymentservice.selectWeektransaction();
    	return amount;
    }
    
    //Detail
    @GetMapping("/detailselectPerMonthamount")
    public List<Map<String, Object>> DetailSelectPerMonthamount() {
    	 List<Map<String, Object>> plist = paymentservice.DetailselectPerMonthamount();
    	return plist;
    }
    @GetMapping("/detailselectLastYearPerMonthamount")
    public List<Map<String, Object>> DetailSelectLastYearPerMonthamount() {
    	 List<Map<String, Object>> plist = paymentservice.DetailSelectLastYearPerMonthamount();
    	return plist;
    }

    @GetMapping("/detailperMonthTotalAmount")
    public int DetailPerMonthTotalAmount() {
    	int totalamount = paymentservice.DetailperMonthTotalAmount();
    	return totalamount;
    }
    @GetMapping("/detailtotalIncrese")
    public Double DetailTotalIncrese() {
    	double totalIncrese = paymentservice.DetailTotalIncrese();
    	return totalIncrese;
    }
    //주별..
    @GetMapping("/detailselectPerWeeklyamount")
    public List<Map<String, Object>> DetailSelectPerWeeklyamount() {
   	 List<Map<String, Object>> plist = paymentservice.DetailselectPerWeeklyamount();
   	return plist;
   }
    
    @GetMapping("/detailselectLastYearPerWeeklyamount")
    public List<Map<String, Object>> DetailSelectLastYearPerWeeklyamount() {
   	 List<Map<String, Object>> plist = paymentservice.DetailSelectLastYearPerWeeklyamount();
   	return plist;
   }
    @GetMapping("/detailperWeekTotalAmount")
    public int DetailperWeekTotalAmount() {
    	int totalamount = paymentservice.DetailperWeekTotalAmount();
    	return totalamount;
    }
    @GetMapping("/detailweektotalIncrese")
    public Double DetailWeekTotalIncrese() {
    	double totalIncrese = paymentservice.DetailWeekTotalIncrese();
    	return totalIncrese;
    }
    
    //월별 거래건수
    @GetMapping("/detailselectPerMonthtransaction")
    public List<Map<String, Object>> DetailSelectPerMonthtransaction() {
   	 List<Map<String, Object>> plist = paymentservice.DetailselectPerMonthtransaction();
   	return plist;
   }
    @GetMapping("/detailselectMonthtransaction")
    public int DetailSelectMonthtransaction() {
    	int amount = paymentservice.DetailselectMonthtransaction();
    	return amount;
    }
    //주간 거래건수
    @GetMapping("/detailselectPerWeeklytransaction")
    public List<Map<String, Object>> DetailSelectPerWeeklytransaction() {
   	 List<Map<String, Object>> plist = paymentservice.DetailselectPerWeeklytransaction();
   	return plist;
   }
    @GetMapping("/detailselectWeektransaction")
    public int DetailselectWeektransaction() {
    	int amount = paymentservice.DetailselectWeektransaction();
    	return amount;
    }
    @GetMapping("/selectTop5CardList")
    public List<Map<String, Object>> SelectTop5CardList() {
   	 List<Map<String, Object>> clist = cardservice.SelectTop5CardList();
   	return clist;
   }
    @GetMapping("/totalCardRegAmount")
	public int TotalCardRegAmount() {
    	int amount = cardreginfoservice.totalcardregamount();
    	return amount;
    }
   @GetMapping("/payAmoutTop5Card")
   public List<Map<String, Object>> PayAmoutTop5Card() {
	   List<Map<String, Object>> plist = paymentservice.PayAmountTop5Card();
	   return plist;
   }
   @GetMapping("/abroadPayAmountTop5Card")
  public List<Map<String, Object>> AbroadPayAmountTop5Card(){
	   List<Map<String, Object>> plist = paymentservice.AbroadPayAmountTop5Card();
	   return plist;
   }
   @GetMapping("benefitTop5Card")
   public List<Map<String, Object>> BenefitTop5Card(){
	   List<Map<String, Object>> blist = paymentservice.benefitTop5Card();
	   return blist;
   }
   @GetMapping("benefitTotalAmount")
   public int benefitTotalAmount() {
	   int count = paymentservice.benefitTotalAmount();
	   return count;
   }
}
