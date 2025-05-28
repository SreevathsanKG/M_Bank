package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	 * AIM: to insert values to customer 
	 * METHOD: POST 
	 * PARAM: Customer -> RequestBody
	 * RESPONSE: Customer 
	 * PATH: /api/customer/post
	 */
	@PostMapping("/post")
	public Customer postustomer(@RequestBody Customer customer) {
		return customerService.postCustomer(customer);
	}

	/*
	 * AIM: fetch customer by id 
	 * METHOD: GET 
	 * PARAM: Customer -> PathVariable
	 * RESPONSE: Customer 
	 * PATH: /api/customer/get-one/{id}
	 */
	@GetMapping("/get-one/{id}")
	public Customer getCustomerById(@PathVariable int id) {
		return customerService.getCustomerByID(id);
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
	 * AIM: update customer
	 * METHOD: PUT
	 * PARAM: CustomerId -> PathVariable, Customer -> RequestBody
	 * RESPONSE: Customer
	 * PATH: /api/customer/put/{id}
	 * */
	@PutMapping("/put/{id}")
	public Customer putCustomer(@PathVariable int id, @RequestBody Customer updatedCustomer) {
		return customerService.putCustomer(id, updatedCustomer);
	}
}
