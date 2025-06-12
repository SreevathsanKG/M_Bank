package com.springboot.bankDemo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanPostDto {

	private BigDecimal interestRate;
	private int termInMonth;
	private LocalDate startDate;
	
	public LoanPostDto() {	}

	public LoanPostDto(BigDecimal interestRate, int termInMonth, LocalDate startDate) {
		this.interestRate = interestRate;
		this.termInMonth = termInMonth;
		this.startDate = startDate;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
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
	
}
