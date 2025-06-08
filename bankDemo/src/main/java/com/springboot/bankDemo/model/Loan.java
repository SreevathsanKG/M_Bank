package com.springboot.bankDemo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.springboot.bankDemo.enums.LoanStatus;

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
@Table(name = "loan")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "principal_amount", nullable = false)
	private BigDecimal principalAmount;
	@Column(name = "total_repayable_amount", nullable = false)
	private BigDecimal totalRepayableAmount;
	@Column(name = "emi_amount", nullable = false)
	private BigDecimal emiAmount;
	@ManyToOne(optional = false)
	private LoanType loanType;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus status;
	@Column(name = "balance_amount", nullable = false)
	private BigDecimal balanceAmount;
	@Column(name = "term_in_month", nullable = false)
	private int termInMonth;
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;
	@ManyToOne(optional = false)
	private LoanApplication loanApplication;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getPrincipalAmount() {
		return principalAmount;
	}
	public void setPrincipalAmount(BigDecimal principalAmount) {
		this.principalAmount = principalAmount;
	}
	public BigDecimal getTotalRepayableAmount() {
		return totalRepayableAmount;
	}
	public void setTotalRepayableAmount(BigDecimal totalRepayableAmount) {
		this.totalRepayableAmount = totalRepayableAmount;
	}
	public BigDecimal getEmiAmount() {
		return emiAmount;
	}
	public void setEmiAmount(BigDecimal emiAmount) {
		this.emiAmount = emiAmount;
	}
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public LoanStatus getStatus() {
		return status;
	}
	public void setStatus(LoanStatus status) {
		this.status = status;
	}
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public int getTermInMonth() {
		return termInMonth;
	}
	public void setTermInMonth(int termInMonth) {
		this.termInMonth = termInMonth;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LoanApplication getLoanApplication() {
		return loanApplication;
	}
	public void setLoanApplication(LoanApplication loanApplication) {
		this.loanApplication = loanApplication;
	}

}
