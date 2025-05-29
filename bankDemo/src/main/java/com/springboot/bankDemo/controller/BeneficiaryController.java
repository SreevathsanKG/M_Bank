package com.springboot.bankDemo.controller;

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
import com.springboot.bankDemo.model.Beneficiary;
import com.springboot.bankDemo.service.BeneficiaryService;

@RestController
@RequestMapping("/api/beneficiary")
public class BeneficiaryController {

	@Autowired
	private BeneficiaryService beneficiaryService;
	
	/*
	 * AIM: to insert values into beneficiary
	 * METHOD: POST
	 * PARAM: Beneficiary -> RequestBody, CustomerId -> PathVariable
	 * RESPONSE: Beneficiary
	 * PATH: /api/beneficiary/post/{customerId}
	 * */
	@PostMapping("/post/{customerId}")
	public ResponseEntity<?> postBeneficiary(@PathVariable int customerId, @RequestBody Beneficiary beneficiary) {
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.postBeneficiary(customerId, beneficiary));
	}
	
	/*
	 * AIM: update values of beneficiary
	 * METHOD: PUT
	 * PARAM: Beneficiary -> RequestBody, 	beneficiaryId,CustomerId -> PathVariable
	 * RESPONSE: Beneficiary
	 * PATH: /api/beneficiary/put/{id}/{customerId}
	 * */
	@PutMapping("/put/{id}/{customerId}")
	public ResponseEntity<?> putBeneficiary(@PathVariable int id, @PathVariable int customerId, 
															@RequestBody Beneficiary beneficiary) {
		return ResponseEntity.status(HttpStatus.OK).body(beneficiaryService.putBeneficiary(id, customerId, beneficiary));
	}
	
	/*
	 * AIM: fetch beneficiary by customerId
	 * METHOD: POST
	 * PARAM: Beneficiary -> RequestBody, CustomerId -> PathVariable
	 * RESPONSE: Beneficiary
	 * PATH: /api/beneficiary/get/{customerId}
	 * */
	@GetMapping("/get/{customerId}")
	public Beneficiary getByCustomerId(@PathVariable int customerId) throws ResourceNotFoundException {
		return beneficiaryService.getByCustomerId(customerId);
	}
}
