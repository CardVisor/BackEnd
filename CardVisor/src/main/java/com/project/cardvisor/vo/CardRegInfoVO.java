package com.project.cardvisor.vo;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

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

@Table(name="card_reg_info")
public class CardRegInfoVO {
	@Id
	private String regId;
	
	@ManyToOne
	@JoinColumn(name="cust_id")
	private CustomerVO custId;
	
	@ManyToOne
	@JoinColumn(name="card_type")
	private CardListVO cardType;
	
	private String cardNum;
	
	private Date regDate;
	
	private Date expireDate;
	
	
	

}
