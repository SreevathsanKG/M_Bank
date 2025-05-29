package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.CustomerExecutive;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.CustomerExecutiveRepository;

@Service
public class CustomerExecutiveService {

	private CustomerExecutiveRepository customerExecutiveRepository;
	private BranchService branchService;
	private UserService userService;

	public CustomerExecutiveService(CustomerExecutiveRepository customerExecutiveRepository,
			BranchService branchService, UserService userService) {
		this.customerExecutiveRepository = customerExecutiveRepository;
		this.branchService = branchService;
		this.userService = userService;
	}

	// add customer-executive
	public CustomerExecutive postCustomerExecutive(int branchId, CustomerExecutive customerExecutive) {
		Branch branch = branchService.getById(branchId);
		customerExecutive.setBranch(branch);
		User user = customerExecutive.getUser();
		user.setRole("CUSTOMER_EXECUTIVE");
		user = userService.signUp(user);
		customerExecutive.setUser(user);
		return customerExecutiveRepository.save(customerExecutive);
	}

	// update customer-executive data
	public CustomerExecutive putCustomerExecutive(String username, CustomerExecutive customerExecutive) {
		CustomerExecutive dbCustomerExecutive = customerExecutiveRepository.getCustomerExecutiveByUsername(username);
		if (customerExecutive.getFirstName() != null)
			dbCustomerExecutive.setFirstName(customerExecutive.getFirstName());
		if (customerExecutive.getLastName() != null)
			dbCustomerExecutive.setLastName(customerExecutive.getLastName());
		if (customerExecutive.getEmail() != null)
			dbCustomerExecutive.setEmail(customerExecutive.getEmail());
		if (customerExecutive.getPhoneNumber() != null)
			dbCustomerExecutive.setPhoneNumber(customerExecutive.getPhoneNumber());
		if (customerExecutive.getAddress() != null)
			dbCustomerExecutive.setAddress(customerExecutive.getAddress());
		return customerExecutiveRepository.save(dbCustomerExecutive);
	}

	// get customer executive by username
	public CustomerExecutive getByUsername(String username) {
		return customerExecutiveRepository.getCustomerExecutiveByUsername(username);
	}

	// get all customer executive
	public List<CustomerExecutive> getAll() {
		return customerExecutiveRepository.findAll();
	}

	// get customer executive under a branch
	public List<CustomerExecutive> getByBranch(int branchId) {
		return customerExecutiveRepository.getCustomerExecutiveByBranch(branchId);
	}
}
