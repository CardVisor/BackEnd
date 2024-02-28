package com.project.cardvisor.vo;

import java.sql.Timestamp;

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

@Table(name = "currency")
public class CurrencyVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int currencyId;
	private String currencyCode;
	private String currencyFlagUrl;
	private String currencyNation;
	private double currencyRate;
	private Timestamp currencyDate;
}
