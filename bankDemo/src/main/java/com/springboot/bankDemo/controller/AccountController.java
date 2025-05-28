package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	/*
	 * AIM: to insert values to account, which requires ifscCode,account-Type,customerId 
	 * METHOD: POST
	 * PARAM: account -> RequestBody, PathVariable <- customerId, branch-ifscCode, accountType-type 
	 * RESPONSE: AccountType 
	 * PATH: /api/accountType/post/{customerId}/{ifscCode}/{type}
	 */
	@PostMapping("/post/{customerId}/{ifscCode}/{type}")
	public Account postAccount(@PathVariable int customerId, @PathVariable String ifscCode, 
							@PathVariable String type, @RequestBody Account account) {
		return accountService.postAccount(customerId, ifscCode, type, account);
	}
	
	/*
	 * AIM: fetch all Account
	 * METHOD: GET 
	 * RESPONSE: Account 
	 * PATH: /api/account/get-all
	 */
	@GetMapping("/get-all")
	public List<Account> getAll() {
		return accountService.getAll();
	}
}
