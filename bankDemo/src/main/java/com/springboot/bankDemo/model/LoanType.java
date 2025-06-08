package com.springboot.bankDemo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_type")
public class LoanType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String type;
	@Column(name = "interes_rate", nullable = false)
	private BigDecimal interestRate;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public BigDecimal getInterestRate() { return interestRate; }
	public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }
	
}
