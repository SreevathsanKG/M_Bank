package com.springboot.bankDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer>{

	@Query("select la from LoanApplication la where la.account.customer.user.username=?1 and la.status=?2")
	List<LoanApplication> getByStatusAndUsername(String username, String status);

	@Query("select la from LoanApplication la where la.status=?1")
	List<LoanApplication> getByStatus(String status);
	
	@Query("select la from LoanApplication la where la.account.branch.id=?1")
	List<LoanApplication> getByBranchId(String branchId);
	
}
