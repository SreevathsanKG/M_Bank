package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.service.EnumsService;

@RestController
@RequestMapping("/api/enums")
public class EnumsController {

	@Autowired
	private EnumsService enumsService;
	
	/*
	 * AIM: fetch all executive role from enum
	 * METHOD: GET
	 * RESPONSE: List<String>
	 * PATH: /api/enums/executive/roles/get
	 * */
	@GetMapping("/executive/roles/get")
	public List<String> getEnumExecutiveRoles() {
		return enumsService.getEnumExecutiveRoles();
	}
	
	/*
	 * AIM: fetch all loan application status from enum
	* METHOD: GET
	 * RESPONSE: List<String>
	 * PATH: /api/enums/loanApply/status/get
	 * */
	@GetMapping("/loanApply/status/get")
	public List<String> getEnumLoanApplicationStatus() {
		return enumsService.getEnumLoanApplicationStatus();
	}
	
	/*
	 * AIM: fetch all loan status from enum
	 * METHOD: GET
	 * RESPONSE: List<String>
	 * PATH: /api/enums/loan/status/get
	 * */
	@GetMapping("/loan/status/get")
	public List<String> getEnumLoanStatus() {
		return enumsService.getEnumLoanStatus();
	}
	
	/*
	 * AIM: fetch all loan type from enum
	 * METHOD: GET
	 * RESPONSE: List<String>
	 * PATH: /api/enums/laon/type/get
	 * */
	@GetMapping("/loan/type/get")
	public List<String> getEnumLoanType() {
		return enumsService.getEnumLoanType();
	}
	/*
	 * AIM: fetch all transfer type from enum
	 * METHOD: GET
	 * RESPONSE: List<String>
	 * PATH: /api/enums/transfer/type/get
	 * */
	@GetMapping("/transfer/type/get")
	public List<String> getEnumTransferType() {
		return enumsService.getEnumTransferType();
	}
}
