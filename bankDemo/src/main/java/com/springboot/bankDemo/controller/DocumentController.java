package com.springboot.bankDemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * AIM: to insert document of customer by loggedIn credentials 
	 * METHOD: POST 
	 * PARAM: Document -> RequestBody, Principal
	 * RESPONSE: Document 
	 * PATH: /api/document/post
	 */
	@PostMapping("/post")
	public Document postDocument(Principal principal, @RequestBody Document document) {
		String username = principal.getName();
		return documentService.postDocument(username, document);
	}
	
	/*
	 * AIM: update customer by loggedIn credentials 
	 * METHOD: PUT 
	 * PARAM: Document -> RequestBody, Principal
	 * RESPONSE: Document 
	 * PATH: /api/document/put
	 */
	@PutMapping("/put")
	public Document putDocument(Principal principal, @RequestBody Document document) {
		String username = principal.getName();
		return documentService.putDocument(username, document);
	}
	
	/*
	 * AIM: fetch document by customer loggedIn credential
	 * METHOD: GET 
	 * PARAM: Principal
	 * RESPONSE: Document 
	 * PATH: /api/document/get
	 */
	@GetMapping("/get")
	public ResponseEntity<?> getDocByUsername(Principal principal) throws ResourceNotFoundException {
		String username = principal.getName();
		return ResponseEntity.status(HttpStatus.OK).body(documentService.getDocByUsername(username));
	}
	
	/*
	 * AIM: fetch document by customer id
	 * METHOD: GET 
	 * PARAM: Customer ID -> PathVariable
	 * RESPONSE: Document 
	 * PATH: /api/document/get-one/{id}
	 */
	@GetMapping("/get-one/{id}")
	public Document getById(@PathVariable int id) throws ResourceNotFoundException {
		return documentService.getByCustomerId(id);
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
