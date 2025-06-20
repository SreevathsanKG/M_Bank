package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.enums.AccountStatus;
import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.model.AccountType;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.repository.AccountRepository;

@Service
public class AccountService {

	private AccountRepository accountRepository;
	private CustomerService customerService;
	private AccountTypeService accountTypeService;
	private BranchService branchService;

	public AccountService(AccountRepository accountRepository, CustomerService customerService,
			AccountTypeService accountTypeService, BranchService branchService) {
		this.accountRepository = accountRepository;
		this.customerService = customerService;
		this.accountTypeService = accountTypeService;
		this.branchService = branchService;
	}

	// insert by username - customer login cred
	public Account postAccountByUsername(String username, String ifscCode, String type, Account account) {
		Customer customer = customerService.getCustomerByUsername(username);
		Branch branch = branchService.getByIfscCode(ifscCode);
		AccountType accountType = accountTypeService.getByType(type);
		boolean exists = accountRepository.getAccountExistsByCustomerandType(customer, accountType);
		if(exists)
			throw new RuntimeException("Customer already has an account of this type");
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		account.setStatus(AccountStatus.PENDING_APPROVAL);
		account.setBranch(branch);
		account.setCustomer(customer);
		return accountRepository.save(account);
	}

	// insert values into account - create account
	public Account postAccount(int customerId, String ifscCode, String type, Account account) {
		Customer customer = customerService.getCustomerById(customerId);
		account.setCustomer(customer);
		Branch branch = branchService.getByIfscCode(ifscCode);
		account.setBranch(branch);
		AccountType accountType = accountTypeService.getByType(type);
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		return accountRepository.save(account);
	}
	
	// update account status
	public Account putAccountStatus(int accountId, String status) {
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		account.setStatus(AccountStatus.valueOf(status));
		return accountRepository.save(account);
	}
	
	// update account balance
	public Account putAccountBalance(int accountId, BigDecimal amtToAdd) {
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal updatedBalance = account.getBalance();
		updatedBalance = updatedBalance.add(amtToAdd);
		account.setBalance(updatedBalance);
		return accountRepository.save(account);
	}
	
	// fetch account by id
	public Account getById(int accountId) {
		return accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}
	
	// fetch account by customer id
	public List<Account> getByCustomerId(int customerId) {
		return accountRepository.getByCustomerId(customerId).orElseThrow(() -> new RuntimeException("Customer has no Account"));
	}
	
	// fetch account by branch id 
	public List<Account> getByBranchId(int  branchId) {
		return accountRepository.getByBranchId(branchId).orElseThrow(() -> new RuntimeException("Branch Id is Invalid"));
	}
	
	
	// fetch account by username
	public List<Account> getByUsername(String username) {
		Customer customer = customerService.getCustomerByUsername(username);
		return accountRepository.getByCustomerId(customer.getId()).orElseThrow(() -> new RuntimeException("Username is Invalid"));
	}
	
	// fetch by status 
	public List<Account> getByStatus(String status) {
		return accountRepository.getByStatus(status).orElseThrow(() -> new RuntimeException("Status is Invalid"));
	}
	
	// fetch all account
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

}
