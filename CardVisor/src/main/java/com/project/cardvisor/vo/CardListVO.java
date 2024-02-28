
package com.project.cardvisor.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Table(name="card_list")
public class CardListVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cardType;
	
	private String cardName;
	private String cardImgUrl;
	private int cardAnnualFee;

}