package com.project.cardvisor.composite;

import java.io.Serializable;

import com.project.cardvisor.vo.BenefitVO;
import com.project.cardvisor.vo.CardListVO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CardBenefitComposite implements Serializable {

	@ManyToOne
	@JoinColumn(name="card_type")
	private CardListVO cardType;
	
	@ManyToOne
	@JoinColumn(name="benefit_id")
	private BenefitVO benefitId;
	
	
}
