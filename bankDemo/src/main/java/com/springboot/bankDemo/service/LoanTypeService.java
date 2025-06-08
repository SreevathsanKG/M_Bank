package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.LoanType;
import com.springboot.bankDemo.repository.LoanTypeRepository;

@Service
public class LoanTypeService {

	private LoanTypeRepository loanTypeRepository;

	public LoanTypeService(LoanTypeRepository loanTypeRepository) {
		this.loanTypeRepository = loanTypeRepository;
	}
	
	// post loan type
	public LoanType postLoanType(LoanType loanType) {
		return loanTypeRepository.save(loanType);
	}
	
	// put-update loan type
	public LoanType putLoanType(int id, LoanType loanType) {
		LoanType dbLoanType = loanTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		if (loanType.getType() != null)
			dbLoanType.setType(loanType.getType());
		if (loanType.getInterestRate() != null)
			dbLoanType.setInterestRate(loanType.getInterestRate());
		return loanTypeRepository.save(dbLoanType);
	}
	
	// fetch by loan type by id
	public LoanType getById(int id) {
		return loanTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	// fetch by type
	public LoanType getByType(String type) {
		return loanTypeRepository.getByType(type).orElseThrow(() -> new RuntimeException("Type is Invalid"));
	}
	
	// fetch all loan type
	public List<LoanType> getAll() {
		return loanTypeRepository.findAll();
	}
}
