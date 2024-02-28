package com.project.cardvisor.vo;

import java.sql.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
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

@Table(name="Customer")
public class CustomerVO {
	
	
	@Id
	private String custId;
	
	private String custName;
	private char custGender;
	private Date custBirth;
	private String custPhone;
	private String custEmail;
	private String custSalary;
	
	@ManyToOne
	@JoinColumn(name="job_id")
	private JobListVO jobId;
	
	

}
