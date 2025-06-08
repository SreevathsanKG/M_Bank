package com.springboot.bankDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Integer>{

	@Query("select lt from LoanType lt where lt.type=?1")
	Optional<LoanType> getByType(String type);
}
