package com.springboot.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.bankDemo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
}
