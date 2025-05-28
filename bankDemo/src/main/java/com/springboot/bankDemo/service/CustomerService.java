package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	// insert values into customer
	public Customer postCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	// fetch customer by id
	public Customer getCustomerByID(int id) {
		return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
	}

	// fetch all customer
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	// update customer
	public Customer putCustomer(int id, Customer updatedCustomer) {
		Customer dbCustomer = customerRepository.findById(id).orElseThrow(()-> new RuntimeException("ID is Invalid"));
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
}
