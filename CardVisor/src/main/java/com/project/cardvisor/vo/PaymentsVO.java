package com.project.cardvisor.vo;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
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

@Table(name="payments")
public class PaymentsVO {
	
	@Id
	private String payId;
	
	@ManyToOne
	@JoinColumn(name="reg_id")
	private CardRegInfoVO regId;
	
	private String nation;
	private String currencyCode;
	private double currencyRate;
	
	private String payStore;
	private long payAmount;
	
	//@CreationTimestamp
	private Timestamp payDate;
	
	@ManyToOne
	@JoinColumn(name="mcc_code")
	private MccVO mccCode;
	
	private int benefitAmount;
	
	private int appliedBenefitId;
	private Date dataInsertDate;

}
