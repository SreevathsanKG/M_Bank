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
	public Document postDocument(String username, Document document) {
		Customer customer = customerRepository.getCustomerByUsername(username);
		document.setCustomer(customer);
		return documentRepository.save(document);
	}
	
	// update document
	public Document putDocument() {
		return null;
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

	public Document putDocument(String username, Document document) {
		Customer customer = customerRepository.getCustomerByUsername(username);
		Document dbDocument = documentRepository.getByCustomerId(customer.getId()).orElseThrow(() -> new RuntimeException("ID is Invalid"));
		if(document.getPanDocLink() != null)
			dbDocument.setPanDocLink(document.getPanDocLink());
		if(document.getAadharDocLink() != null)
			dbDocument.setAadharDocLink(document.getAadharDocLink());
		return documentRepository.save(dbDocument);
	}

	public Object getDocByUsername(String username) throws ResourceNotFoundException {
		Customer customer = customerRepository.getCustomerByUsername(username);
		return documentRepository.getByCustomerId(customer.getId())			
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found for given Customer Id"));
	}
}
