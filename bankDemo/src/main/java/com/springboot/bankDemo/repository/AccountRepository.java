package com.springboot.bankDemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	@Query("select a from Account a where a.customer.id=?1")
	Optional<List<Account>> getByCustomerId(int customerId);

	@Query("select a from Account a where a.branch.ifscCode=?1")
	Optional<List<Account>> getByIfscCode(String ifscCode);

	@Query("select a from Account a where a.status=?1")
	Optional<List<Account>> getByStatus(String status);
}
