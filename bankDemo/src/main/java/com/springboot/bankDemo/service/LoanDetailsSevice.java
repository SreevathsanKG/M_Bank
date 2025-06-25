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
		loanDetails.setTotalRepayableAmount(amount.add(amount.multiply(loanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
		loanDetails.setEmiAmount(loanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(loanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
		return loanDetailsRepository.save(loanDetails);
	}
	
	public LoanDetails getById(int id) {
		return loanDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	public List<LoanDetails> getAll() {
		return loanDetailsRepository.findAll();
	}

	public LoanDetails putLoanDetails(int id, LoanDetails loanDetails) {
		LoanDetails dbLoanDetails = loanDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		if(loanDetails.getLoanType() != null)
			dbLoanDetails.setLoanType(loanDetails.getLoanType());
		if(loanDetails.getPrincipalAmount() != BigDecimal.valueOf(0)) {
			dbLoanDetails.setPrincipalAmount(loanDetails.getPrincipalAmount());
			BigDecimal amount = loanDetails.getPrincipalAmount();
			dbLoanDetails.setTotalRepayableAmount(amount.add(amount.multiply(dbLoanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
			dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(dbLoanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
		}
		if(loanDetails.getInterestRate() != BigDecimal.valueOf(0)) {
			dbLoanDetails.setInterestRate(loanDetails.getInterestRate());
			BigDecimal amount = dbLoanDetails.getPrincipalAmount();
			dbLoanDetails.setTotalRepayableAmount(amount.add(amount.multiply(loanDetails.getInterestRate().divide(BigDecimal.valueOf(100)))));
			dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(dbLoanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
		}
		if(loanDetails.getTermInMonth() != 0) {
			dbLoanDetails.setTermInMonth(loanDetails.getTermInMonth());
			dbLoanDetails.setEmiAmount(dbLoanDetails.getTotalRepayableAmount().divide(BigDecimal.valueOf(loanDetails.getTermInMonth()), 2, RoundingMode.HALF_UP));
		}
		return loanDetailsRepository.save(dbLoanDetails);
	}

	public void deleteLoanDetails(int id) {
		loanDetailsRepository.deleteById(id);
	}
}
