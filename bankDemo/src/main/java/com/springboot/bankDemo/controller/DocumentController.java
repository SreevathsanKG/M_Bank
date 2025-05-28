package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.exception.ResourceNotFoundException;
import com.springboot.bankDemo.model.Document;
import com.springboot.bankDemo.service.DocumentService;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;
	
	/*
	 * AIM: to insert document of customer 
	 * METHOD: POST 
	 * PARAM: Document -> RequestBody, customerId -> PathVariable
	 * RESPONSE: Document 
	 * PATH: /api/document/post/{customerId}
	 */
	@PostMapping("/post/{customerId}")
	public Document postDocument(@PathVariable int customerId, @RequestBody Document document) {
		return documentService.postDocument(customerId, document);
	}
	
	/*
	 * AIM: fetch document by customer id
	 * METHOD: GET 
	 * PARAM: customerId -> RequestParam
	 * RESPONSE: Document 
	 * PATH: /api/document/get/customerId?customerIf=3
	 */
	@GetMapping("/get/customerId")
	public ResponseEntity<?> getByCustomerId(@RequestParam int customerId) throws ResourceNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(documentService.getByCustomerId(customerId));
	}
	
	/*
	 * AIM: fetch all document
	 * METHOD: GET
	 * RESPONSE: List<Document>
	 * PATH: /api/document/get-all
	 */
	@GetMapping("/get-all")
	public List<Document> getAll() {
		return documentService.getAll();
	}
	
}
