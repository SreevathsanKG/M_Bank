package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.dto.LoanPostDto;
import com.springboot.bankDemo.enums.LoanStatus;
import com.springboot.bankDemo.model.Loan;
import com.springboot.bankDemo.model.LoanApplication;
import com.springboot.bankDemo.repository.LoanApplicationRepository;
import com.springboot.bankDemo.repository.LoanRepository;

@Service
public class LoanService {

	private LoanRepository loanRepository;
	private LoanApplicationRepository loanApplicationRepository;
	
	public LoanService(LoanRepository loanRepository, LoanApplicationRepository loanApplicationRepository) {
		this.loanRepository = loanRepository;
		this.loanApplicationRepository = loanApplicationRepository;
	}
	
	// post loan with loan application
	public Loan postLoan(int loanApplicationId, LoanPostDto loanPostDto) {
		Loan loan = new Loan();
		LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = loanApplication.getRequiredLoanAmount();
		loan.setPrincipalAmount(amount);
		loan.setTotalRepayableAmount(amount.add(amount.multiply(loanPostDto.getInterestRate())));
		loan.setLoanType(loanApplication.getLoanType());
		loan.setInterestRate(loanPostDto.getInterestRate());
		loan.setStatus(LoanStatus.ACTIVE);
		loan.setTermInMonth(loanPostDto.getTermInMonth());
		loan.setEmiAmount(amount.divide(BigDecimal.valueOf(loanPostDto.getTermInMonth())));
		loan.setBalanceAmount(amount);
		loan.setStartDate(loanPostDto.getStartDate());
		loan.setEndDate(loanPostDto.getStartDate().plusMonths(loanPostDto.getTermInMonth()));
		loan.setLoanApplication(loanApplication);
		return null;
	}
	
	// update loan balance
	public Loan putLoanBalance(int id, BigDecimal paidAmount) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		loan.setBalanceAmount(loan.getBalanceAmount().subtract(paidAmount));
		return loanRepository.save(loan);
	}
	
	// update loan status
	public Loan putLoanStatus(int id, String status) {
		Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		loan.setStatus(LoanStatus.valueOf(status));
		return loanRepository.save(loan);
	}
	
	// fetch loan by branchId
	public List<Loan> getByBranchId(int branchId) {
		return loanRepository.getByBranchId(branchId);
	}
	
	// fetch loan by ID
	public Loan getById(int id) {
		return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	// fetch all loan
	public List<Loan> getAll() {
		return loanRepository.findAll();
	}
}
