package com.springboot.bankDemo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Transaction;
import com.springboot.bankDemo.repository.TransactionRepository;

@Service
public class TransactionService {

	public TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	// fetch transaction between given date
	public List<Transaction> getTransactionBetweenDate(Date fromDate, Date tillDate) {
		return transactionRepository.getTransactionBetweenDate(fromDate, tillDate);
	}
	
	// fetch transaction from a given date to till now
	public List<Transaction> getTransactionsFromDate(Date fromDate) {
		return transactionRepository.getTransactionFromDate(fromDate);
	}
	
	// fetch transaction till a given date
	public List<Transaction> getTransactionTillDate(Date tillDate) {
		return transactionRepository.getTransactionTillDate(tillDate);
	}
	
	// fetch transaction between given date of an account by loggedIn cred
	// fetch transaction from  given date of an account by loggedIn cred
	// fetch transaction till given date of an account by loggedIn cred
	
	// fetch transaction by accountId between given date
	// fetch transaction by accountId from given date
	// fetch transaction by accountId till given date
	
	// fetch all
	public List<Transaction> getAll() {
		return transactionRepository.findAll();
	}
}
