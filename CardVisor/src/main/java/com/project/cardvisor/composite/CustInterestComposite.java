package com.project.cardvisor.composite;

import java.io.Serializable;

import com.project.cardvisor.vo.CustomerVO;
import com.project.cardvisor.vo.InterestVO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CustInterestComposite implements Serializable {
	
	@ManyToOne
	@JoinColumn(name="cust_id")
	private CustomerVO custId;
	
	@ManyToOne
	@JoinColumn(name="interest_id")
	private InterestVO interestId;
	
	
}
