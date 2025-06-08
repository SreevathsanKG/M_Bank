package com.springboot.bankDemo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.springboot.bankDemo.enums.LoanApplicationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_application")
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "required_loan_amount", nullable = false)
	private BigDecimal requiredLoanAmount;
	@ManyToOne(optional = false)
	private LoanType loanType;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LoanApplicationStatus status;
	@Column
	private String remark;
	@Column(nullable = false)
	private String document;
	@Column(name = "application_date", nullable = false)
	private LocalDate applicationDate;
	@ManyToOne(optional = false)
	private Account account;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public BigDecimal getRequiredLoanAmount() { return requiredLoanAmount; }
	public void setRequiredLoanAmount(BigDecimal requiredLoanAmount) { this.requiredLoanAmount = requiredLoanAmount; }
	public LoanType getLoanType() { return loanType; }
	public void setLoanType(LoanType loanType) { this.loanType = loanType; }
	public LoanApplicationStatus getStatus() { return status; }
	public void setStatus(LoanApplicationStatus status) { this.status = status; }
	public String getRemark() { return remark; }
	public void setRemark(String remark) { this.remark = remark; }
	public String getDocument() { return document; }
	public void setDocument(String document) { this.document = document; }
	public LocalDate getApplicationDate() { return applicationDate; }
	public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
	public Account getAccount() { return account; }
	public void setAccount(Account account) { this.account = account; }
	
}
