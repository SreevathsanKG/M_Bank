package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	private UserService userService;

	public CustomerService(CustomerRepository customerRepository, UserService userService) {
		super();
		this.customerRepository = customerRepository;
		this.userService = userService;
	}

	// insert values into customer
	public Customer postCustomer(Customer customer) {
		User user = customer.getUser();
		user.setRole("CUSTOMER");
		user = userService.signUp(user);
		customer.setUser(user);
		return customerRepository.save(customer);
	}

	// fetch customer by id
	public Customer getCustomerById(int id) {
		return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}

	// fetch all customer
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	// update customer
	public Customer putCustomer(String username, Customer updatedCustomer) {
		Customer dbCustomer = customerRepository.getCustomerByUsername(username);
		if(updatedCustomer.getFirstName() != null)
			dbCustomer.setFirstName(updatedCustomer.getFirstName());
		if(updatedCustomer.getLastName() != null)
			dbCustomer.setLastName(updatedCustomer.getLastName());
		if(updatedCustomer.getEmail() != null)
			dbCustomer.setEmail(updatedCustomer.getEmail());
		if(updatedCustomer.getPhoneNumber() != null)
			dbCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
		if(updatedCustomer.getAddress() != null)
			dbCustomer.setAddress(updatedCustomer.getAddress());
		return customerRepository.save(dbCustomer);
	}

	// fetch customer by log Cred
	public Customer getCustomerByUsername(String username) {
		return customerRepository.getCustomerByUsername(username);
	}
	
}
