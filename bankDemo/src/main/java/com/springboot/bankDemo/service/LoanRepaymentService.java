package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Loan;
import com.springboot.bankDemo.model.LoanRepayment;
import com.springboot.bankDemo.repository.LoanRepaymentRepository;
import com.springboot.bankDemo.repository.LoanRepository;

@Service
public class LoanRepaymentService {

	private  LoanRepaymentRepository loanRepaymentRepository;
	private LoanService loanService;
	private LoanRepository loanRepository;
	
	public LoanRepaymentService(LoanRepaymentRepository loanRepaymentRepository, LoanService loanService, LoanRepository loanRepository) {
		this.loanRepaymentRepository = loanRepaymentRepository;
		this.loanService = loanService;
		this.loanRepository = loanRepository;
	}
	
	// post loan repayment
	public LoanRepayment postLoanRepayment(int loanId, BigDecimal amount) {
		Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		loanService.putLoanBalance(loanId, amount);
		LoanRepayment loanRepayment = new LoanRepayment();
		loanRepayment.setRepaymentAmount(amount);
		loanRepayment.setRepaymentDate(LocalDate.now());
		loanRepayment.setLoan(loan);
		return loanRepaymentRepository.save(loanRepayment);
	}
	
	// fetch loan repayment by loan id
	public List<LoanRepayment> getByLoanId(int loanId) {
		return loanRepaymentRepository.getByLoanId(loanId);
	}
	
}
