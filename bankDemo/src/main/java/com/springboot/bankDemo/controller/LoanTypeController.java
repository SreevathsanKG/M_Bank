package com.springboot.bankDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.LoanType;
import com.springboot.bankDemo.service.LoanTypeService;

@RestController
@RequestMapping("/api/loanType")
public class LoanTypeController {

	@Autowired
	private LoanTypeService loanTypeService;
	
	/*
	 * AIM: insert loan type
	 * PARAM: RequestBody -> LoanType
	 * METHOD: POST
	 * RESPONSE: LoanType
	 * PATH: /api/loanType/post
	 * */
	@PostMapping("/get")
	public LoanType postLoanType(@RequestBody LoanType loanType) {
		return loanTypeService.postLoanType(loanType);
	}
}
