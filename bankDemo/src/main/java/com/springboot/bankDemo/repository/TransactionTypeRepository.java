package com.springboot.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer>{

	@Query("select tt from TransactionType tt where tt.type=?1")
	TransactionType getByType(String type);

}
