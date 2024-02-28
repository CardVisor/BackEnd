package com.project.cardvisor.dto;

import java.util.List;

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
public class CustClusterStatsDTO {
	
	private Long count;
    private Double averageAge;
    private List<String> distinctSalaries;
    private Double averagePayment;
    private List<Object[]> cardName;
    private List<Object[]> cardMcc;

}
