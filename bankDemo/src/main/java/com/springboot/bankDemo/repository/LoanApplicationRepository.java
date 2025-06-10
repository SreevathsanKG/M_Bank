package com.springboot.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.bankDemo.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer>{

	
}
