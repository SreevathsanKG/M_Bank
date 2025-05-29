package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.exception.ResourceNotFoundException;
import com.springboot.bankDemo.model.Beneficiary;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.repository.BeneficiaryRepository;
import com.springboot.bankDemo.repository.CustomerRepository;

@Service
public class BeneficiaryService {

	private BeneficiaryRepository beneficiaryRepository;
	private CustomerRepository customerRepository;
	
	public BeneficiaryService(BeneficiaryRepository beneficiaryRepository, CustomerRepository customerRepository) {
		this.beneficiaryRepository = beneficiaryRepository;
		this.customerRepository = customerRepository;
	}

	// insert values into beneficiary
	public Beneficiary postBeneficiary(int customerId, Beneficiary beneficiary) {
		Customer customer = customerRepository.findById(null).orElseThrow(() -> new RuntimeException("Invalid Customer ID"));
		beneficiary.setCustomer(customer);
		return beneficiaryRepository.save(beneficiary);
	}
	
	// update beneficiary
	public Beneficiary putBeneficiary(int id, int customerId, Beneficiary beneficiary) {
		Beneficiary dbBeneficiary = beneficiaryRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer ID is Invalid"));
		if(beneficiary.getName() != null)
			dbBeneficiary.setName(beneficiary.getName());
		if(beneficiary.getAccountNumber() != null)
			dbBeneficiary.setAccountNumber(beneficiary.getAccountNumber());
		if(beneficiary.getIfscCode() != null)
			dbBeneficiary.setIfscCode(beneficiary.getIfscCode());
		if(beneficiary.getBranchName() != null)
			dbBeneficiary.setBranchName(beneficiary.getBranchName());
		if(beneficiary.getDescription() != null)
			dbBeneficiary.setDescription(beneficiary.getDescription());
		dbBeneficiary.setCustomer(customer);
		return beneficiaryRepository.save(dbBeneficiary);
	}
	
	// fetch by customer id
	public Beneficiary getByCustomerId(int customerId) throws ResourceNotFoundException {
		customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer ID is Invalid"));
		return beneficiaryRepository.getByCustomerId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));  // user writes JPQL
//		return beneficiaryRepository.findByCustomerId(customerId)
//				.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));  // Jpa writes JPQL
	}
	
	// fetch by id
	public Beneficiary getById(int id) {
		return beneficiaryRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	// fetch all beneficiary
	public List<Beneficiary> getAll() {
		return beneficiaryRepository.findAll();
	}
}
