package com.springboot.bankDemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.Executive;
import com.springboot.bankDemo.model.Manager;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.CustomerRepository;
import com.springboot.bankDemo.repository.ExecutiveRepository;
import com.springboot.bankDemo.repository.ManagerRepository;
import com.springboot.bankDemo.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;
	private ExecutiveRepository executiveRepository;
	private ManagerRepository managerRepository;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			CustomerRepository customerRepository, ExecutiveRepository executiveRepository,
			ManagerRepository managerRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.executiveRepository = executiveRepository;
		this.managerRepository = managerRepository;
	}

	public User signUp(User user) {
		String plainPassoword = user.getPassword();							// plain password
		String encodedPassword = passwordEncoder.encode(plainPassoword);	// encodes the plain password
		user.setPassword(encodedPassword);									// encrypted password set to user password 
		return userRepository.save(user);
	}
	
	public Object getUserInfo(String username) {
		User user = userRepository.getByUsername(username);
		switch(user.getRole()) {
		case "CUSTOMER":
			Customer customer = customerRepository.getCustomerByUsername(username);
			return customer;
		case "CUSTOMER_EXECUTIVE":
			Executive executive = executiveRepository.getExecutiveByUsername(username);
			return executive;
		case "FINANCE_EXECUTIVE":
			return null;
		case "LOAN_EXECUTIVE":
			return null;
		case "MANAGER":
			Manager manager = managerRepository.getManagerByUsername(username);
			return manager;
		default:
			return null;
		}
	}
}
