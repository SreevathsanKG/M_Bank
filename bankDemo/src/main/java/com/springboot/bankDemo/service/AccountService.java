package com.springboot.bankDemo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.model.AccountType;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.repository.AccountRepository;
import com.springboot.bankDemo.repository.AccountTypeRepository;
import com.springboot.bankDemo.repository.BranchRepository;
import com.springboot.bankDemo.repository.CustomerRepository;

@Service
public class AccountService {

	private AccountRepository accountRepository;
	private CustomerRepository customerRepository;
	private AccountTypeRepository accountTypeRepository;
	private BranchRepository branchRepository;
	
	public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository,
			AccountTypeRepository accountTypeRepository, BranchRepository branchRepository) {
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
		this.accountTypeRepository = accountTypeRepository;
		this.branchRepository = branchRepository;
	}

	// insert values into account - create account
	public Account postAccount(int customerId, String ifscCode, String type, Account account) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		account.setCustomer(customer);
		Branch branch = branchRepository.getByIfscCode(ifscCode).orElseThrow(()-> new RuntimeException("ifscCode is Invalid"));
		account.setBranch(branch);
		AccountType accountType = accountTypeRepository.getByType(type).orElseThrow(()-> new RuntimeException("Type is Invalid"));
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		account.setStatus("PENDING_APPROVAL");
		return accountRepository.save(account);
	}
	
	// update account status
	
	// update account balance
	
	// fetch account by id
	
	// fetch account by customer id
	
	// fetch account by branch id or ifscCode
	
	// fetch all account
	public List<Account> getAll() {
		return accountRepository.findAll();
	}
}
