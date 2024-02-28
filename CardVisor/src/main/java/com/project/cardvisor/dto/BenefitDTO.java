package com.project.cardvisor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BenefitDTO {
	
	private String ctg_name;
	private String mcc_code;
	private Integer benefit_amount_sum;
	private Integer benefit_id;
	private String benefit_detail;
	private double benefit_pct;
	private String card_name;
	private Integer interest_id;

}

