package com.springboot.bankDemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/*
	 * AIM: to insert values to customer with user info
	 * METHOD: POST 
	 * PARAM: Customer -> RequestBody
	 * RESPONSE: Customer 
	 * PATH: /api/customer/post
	 */
	@PostMapping("/post")
	public Customer postCustomer(@RequestBody Customer customer) {
		return customerService.postCustomer(customer);
	}

	/*
	 * AIM: fetch customer by loggedIn credentials
	 * METHOD: GET 
	 * PARAM: Customer -> PathVariable
	 * RESPONSE: Customer 
	 * PATH: /api/customer/get-one
	 */
	@GetMapping("/get-one")
	public Customer getCustomerByUsername(Principal principal) {
		String username = principal.getName();
		return customerService.getCustomerByUsername(username);
	}

	/*
	 * AIM: fetch all customer 
	 * METHOD: GET 
	 * RESPONSE: List<Customer> 
	 * PATH:
	 * /api/customer/get-all
	 */
	@GetMapping("/get-all")
	public List<Customer> getAll() {
		return customerService.getAll();
	}
	/*
	 * AIM: update customer by loggedIn credential
	 * METHOD: PUT
	 * PARAM: CustomerId -> PathVariable, Customer -> RequestBody
	 * RESPONSE: Customer
	 * PATH: /api/customer/put
	 * */
	@PutMapping("/put")
	public Customer putCustomer(Principal principal, @RequestBody Customer updatedCustomer) {
		String username = principal.getName();
		return customerService.putCustomer(username, updatedCustomer);
	}
}
