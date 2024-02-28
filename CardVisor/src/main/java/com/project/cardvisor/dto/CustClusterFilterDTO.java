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
public class CustClusterFilterDTO {
	

	private List<String> gender;
	private List<Integer> ageRange;
	private String jobType;
	private String salaryRange;
   


}
