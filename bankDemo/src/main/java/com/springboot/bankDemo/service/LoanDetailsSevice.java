package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.LoanDetails;
import com.springboot.bankDemo.repository.LoanDetailsRepository;

@Service
public class LoanDetailsSevice {
	
	private LoanDetailsRepository loanDetailsRepository;

	public LoanDetailsSevice(LoanDetailsRepository loanDetailsRepository) {
		this.loanDetailsRepository = loanDetailsRepository;
	}
	
	public LoanDetails postLoanDetails(LoanDetails loanDetails) {
		BigDecimal amount = loanDetails.getPrincipalAmount();
		loanDetails.setTotalRepayableAmount(amount.add(amount.multiply(loanDetails.getInterestRate())));
		loanDetails.setEmiAmount(amount.divide(BigDecimal.valueOf(loanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
		return loanDetailsRepository.save(loanDetails);
	}
	
	public LoanDetails getById(int id) {
		return loanDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	public List<LoanDetails> getAll() {
		return loanDetailsRepository.findAll();
	}
}
