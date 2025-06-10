package com.springboot.bankDemo.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.enums.LoanApplicationStatus;
import com.springboot.bankDemo.enums.LoanType;
import com.springboot.bankDemo.model.LoanApplication;
import com.springboot.bankDemo.repository.LoanApplicationRepository;

@Service
public class LoanApplicationService {

	private LoanApplicationRepository loanApplicationRepository;

	public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
		this.loanApplicationRepository = loanApplicationRepository;
	}
	
	// post loan application
	public LoanApplication postLoanApplication(LoanApplication loanApplication) {
		loanApplication.setStatus(LoanApplicationStatus.PENDING);
		loanApplication.setApplicationDate(LocalDate.now());
		return loanApplicationRepository.save(loanApplication);
	}
	
	// change loan application status - cancelled by customer
	public LoanApplication putLoanApplicationStatusCancelled(int id) {
		LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		loanApplication.setStatus(LoanApplicationStatus.CANCELLED);
		return loanApplicationRepository.save(loanApplication);
	}
	
	// change loan application status
	public LoanApplication putLoanApplicationStatus(int id, String status, String remark) {
		LoanApplication loanApplication = loanApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		loanApplication.setStatus(LoanApplicationStatus.valueOf(status));
		loanApplication.setRemark(remark);
		return loanApplicationRepository.save(loanApplication);
	}
	
	// 
	
	
	// fetch loan application status
	public List<String> getLoanApplicationStatus() {
		List<String> list = Arrays.stream(LoanApplicationStatus.values()).map(l -> l.name()).toList();
		return list;
	}
	
	// fetch loan type 
	public List<String> getLoanType() {
		List<String> list = Arrays.stream(LoanType.values()).map(l -> l.name()).toList();
		return list;
	}
}
