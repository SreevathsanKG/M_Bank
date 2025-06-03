package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.TransactionType;
import com.springboot.bankDemo.service.TransactionTypeService;

@RestController
@RequestMapping("/api/tt")
public class TransactionTypeController {

	@Autowired
	private TransactionTypeService transactionTypeService;
	
	/*
	 * AIM: insert transaction type
	 * METHOD: POST
	 * PARAM:  TransactionType -> RequestBody
	 * RESPONSE: TT
	 * PATH: /api/tt/post
	 */
	@PostMapping("/post")
	public TransactionType postTransactionType(@RequestBody TransactionType transactionType) {
		return transactionTypeService.postTransactionType(transactionType);
	}
	
	/*
	 * AIM: update TT
	 * METHOD: PUT
	 * PARAM:  TransactionType -> RequestBody, Id -> PathVariable
	 * RESPONSE: TT
	 * PATH: /api/tt/put{id}
	 */
	@PutMapping("/put/{id}")
	public TransactionType putTransactionType(@PathVariable int id, @RequestBody TransactionType transactionType) {
		return transactionTypeService.putTransactionType(id, transactionType);
	}
	
	/*
	 * AIM: fetch by type
	 * METHOD: GET
	 * PARAM:  type -> PathVariable
	 * RESPONSE: TT
	 * PATH: /api/tt/get/type/{type}
	 */
	@GetMapping("/get/type/{type}")
	public TransactionType getByType(@PathVariable String type) {
		return transactionTypeService.getByType(type);
	}
	
	/*
	 * AIM: fetch all transaction type
	 * METHOD: GET
	 * RESPONSE: List<TT>
	 * PATH: /api/tt/get-all
	 */
	@GetMapping("/get-all")
	public List<TransactionType> getAll() {
		return transactionTypeService.getAll();
	}
}
