package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.TransactionType;
import com.springboot.bankDemo.repository.TransactionTypeRepository;

@Service
public class TransactionTypeService {

	private TransactionTypeRepository transactionTypeRepository;

	public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
		this.transactionTypeRepository = transactionTypeRepository;
	}
	
	// insert account type
	public TransactionType postTransactionType(TransactionType transactionType) {
		return transactionTypeRepository.save(transactionType);
	}
	
	// update transaction type
	public TransactionType putTransactionType(int id, TransactionType transactionType) {
		TransactionType dbTransactionType = transactionTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		if(transactionType.getType() != null)
			dbTransactionType.setType(transactionType.getType());
		if(transactionType.getType() != null)
			dbTransactionType.setFee(transactionType.getFee());
		return transactionTypeRepository.save(dbTransactionType);
	}
	
	// fetch by type
	public TransactionType getByType(String type) {
		return transactionTypeRepository.getByType(type);
	}
	
	// fetch all
	public List<TransactionType> getAll() {
		return transactionTypeRepository.findAll();
	}
}
