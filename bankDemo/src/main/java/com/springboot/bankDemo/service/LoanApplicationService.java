package com.springboot.bankDemo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.enums.LoanApplicationStatus;
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
		boolean exists = loanApplicationRepository.getLoanAppExistsByCustomerAndType(loanApplication.getAccount().getCustomer(), loanApplication.getLoanType());
		if(exists)
			throw new RuntimeException("Customer has already applied or has the Loan of this type");
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
	
	// fetch loan application by status by user login-cred
	public List<LoanApplication> getByStatusAndUsername(String username, String status) {
		return loanApplicationRepository.getByStatusAndUsername(username, status);
	}
	
	// fetch all loan application by status
	public List<LoanApplication> getByStatus(String status) {
		return loanApplicationRepository.getByStatus(status);
	}
	
	// fetch loan application by branchId
	public List<LoanApplication> getByBranchId(String branchId) {
		return loanApplicationRepository.getByBranchId(branchId);
	}
	
	// fetch all loan application
	public List<LoanApplication> getAll() {
		return loanApplicationRepository.findAll();
	}
}
