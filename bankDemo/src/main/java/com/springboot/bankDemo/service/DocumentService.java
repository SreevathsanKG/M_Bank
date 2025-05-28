package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.exception.ResourceNotFoundException;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.Document;
import com.springboot.bankDemo.repository.CustomerRepository;
import com.springboot.bankDemo.repository.DocumentRepository;

@Service
public class DocumentService {

	private DocumentRepository documentRepository;
	private CustomerRepository customerRepository;
	
	public DocumentService(DocumentRepository documentRepository, CustomerRepository customerRepository) {
		this.documentRepository = documentRepository;
		this.customerRepository = customerRepository;
	}

	// insert document
	public Document postDocument(int customerId, Document document) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Invalid Customer ID"));
		document.setCustomer(customer);
		return documentRepository.save(document);
	}

	// fetch document by customer id
	public Document getByCustomerId(int customerId) throws ResourceNotFoundException {
		customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Invalid Customer ID"));
		return documentRepository.getByCustomerId(customerId)				// call user written method JPQL
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Customer Id"));
//		return documentRepository.findByCustomerId(customerId)				// calls Jpa written JPQL method
//				.orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Customer Id"));
	}
	
	// fetch all document
	public List<Document> getAll() {
		return documentRepository.findAll();
	}
}
