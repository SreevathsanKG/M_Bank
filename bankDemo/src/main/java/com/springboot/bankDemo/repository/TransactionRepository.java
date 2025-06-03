package com.springboot.bankDemo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.bankDemo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query("select t from Transaction t where t.transactionDate between ?1 and ?2")
	List<Transaction> getTransactionBetweenDate(Date fromDate, Date tillDate);

	@Query("select t from Transaction t where t.transactionDate>=?1")
	List<Transaction> getTransactionFromDate(Date fromDate);

	@Query("select t from Transaction t where t.transactionDate<=?1")
	List<Transaction> getTransactionTillDate(Date tillDate);
}
