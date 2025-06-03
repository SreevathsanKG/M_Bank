package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

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
		account.setCustomer(customer);
		Branch branch = branchService.getByIfscCode(ifscCode);
		account.setBranch(branch);
		AccountType accountType = accountTypeService.getByType(type);
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		account.setStatus("PENDING_APPROVAL");
		return accountRepository.save(account);
	}

	// insert values into account - create account
	public Account postAccount(int customerId, String ifscCode, String type, Account account) {
		Customer customer = customerService.getCustomerByID(customerId);
		account.setCustomer(customer);
		Branch branch = branchService.getByIfscCode(ifscCode);
		account.setBranch(branch);
		AccountType accountType = accountTypeService.getByType(type);
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		account.setStatus("PENDING_APPROVAL");
		return accountRepository.save(account);
	}
	
	// update account status
	public Account putAccountStatus(int accountId, String status) {
		Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		account.setStatus(status);
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
		return accountRepository.getByCustomerId(customerId);
	}
	
	// fetch account by branch id or ifscCode
	public List<Account> getByIfscCode(String ifscCode) {
		return accountRepository.getByIfscCode(ifscCode);
	}
	
	// fetch account by username
	public List<Account> getByUsername(String username) {
		Customer customer = customerService.getCustomerByUsername(username);
		return accountRepository.getByCustomerId(customer.getId());
	}
	
	// fetch by status 
	public List<Account> getByStatus(String status) {
		return accountRepository.getByStatus(status);
	}
	
	// fetch all account
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

}
