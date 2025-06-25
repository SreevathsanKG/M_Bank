package com.springboot.bankDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.LoanDetails;
import com.springboot.bankDemo.service.LoanDetailsSevice;

@RestController
@RequestMapping("/api/loanDetails")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanDetailsController {

	@Autowired
	private LoanDetailsSevice loanDetailsSevice;
	
	@PostMapping("/post")
	public LoanDetails postLoanDetails(@RequestBody LoanDetails loanDetails) {
		return loanDetailsSevice.postLoanDetails(loanDetails);
	}
	
	@PutMapping("/put/{id}")
	public LoanDetails putLoanDetails(@PathVariable int id, @RequestBody LoanDetails loanDetails) {
		return loanDetailsSevice.putLoanDetails(id, loanDetails);
	}
	
	@GetMapping("/get/id/{id}")
	public LoanDetails getById(@PathVariable int id) {
		return loanDetailsSevice.getById(id);
	}
	
	@GetMapping("/get/all")
	public List<LoanDetails> getAll() {
		return loanDetailsSevice.getAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteLoanDetails(@PathVariable int id) {
		loanDetailsSevice.deleteLoanDetails(id);
	}
}
