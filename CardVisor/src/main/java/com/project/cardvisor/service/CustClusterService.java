package com.project.cardvisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cardvisor.repo.CustomerRepository;
import com.project.cardvisor.vo.CustomerVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class CustClusterService {

	@Autowired
    CustomerRepository crepo;

    public Map<String, Long> getGenderRatio() {
        List<CustomerVO> customers = (List<CustomerVO>) crepo.findAll();
        long maleCount = customers.stream().filter(customer -> customer.getCust_gender() == '남').count();
        long femaleCount = customers.stream().filter(customer -> customer.getCust_gender() == '여').count();

        Map<String, Long> genderRatio = new HashMap<>();
        genderRatio.put("남성", maleCount);
        
        genderRatio.put("여성", femaleCount);

        return genderRatio;
    }
    
    public int CustomerTotalCount() {
    	List<CustomerVO> customers = (List<CustomerVO>) crepo.findAll();
    	int CustomerCount = customers.size();
    	return CustomerCount;
    }
}
