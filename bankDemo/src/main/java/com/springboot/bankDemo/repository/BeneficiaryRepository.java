package com.springboot.bankDemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer>{

	@Query("select b from Beneficiary b where b.customer.id=?1")
	Optional<Beneficiary> getByCustomerId(int customerId);		// user written JPQL method to fetch beneficiary by customer id
	Optional<Beneficiary> findByCustomerId(int customerId);		// Jpa writes JPQL to fetch beneficiary by customer id
}
