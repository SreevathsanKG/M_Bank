package com.springboot.bankDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.CustomerExecutive;

public interface CustomerExecutiveRepository extends JpaRepository<CustomerExecutive, Integer>{

	@Query("select ce from CustomerExecutive ce where ce.user.username=?1")
	CustomerExecutive getCustomerExecutiveByUsername(String username);
	
	@Query("select ce from CustomerExecutive ce where ce.branch.id=?1")
	List<CustomerExecutive> getCustomerExecutiveByBranch(int branchId);
}
