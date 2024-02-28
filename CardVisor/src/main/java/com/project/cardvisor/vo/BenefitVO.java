package com.project.cardvisor.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

@Entity

@Table(name="benefit")
public class BenefitVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int benefitId;
	
	private String benefitDetail;
		
	private String mccCode;
	
	private double benefitPct;
	
	@ManyToOne
	@JoinColumn(name="interest_id")
	private InterestVO interestId;
	

}

