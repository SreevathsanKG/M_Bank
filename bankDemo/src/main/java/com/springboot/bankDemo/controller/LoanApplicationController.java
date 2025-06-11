package com.springboot.bankDemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.LoanApplication;
import com.springboot.bankDemo.service.LoanApplicationService;

@RestController
@RequestMapping("/api/loanApply")
public class LoanApplicationController {

	@Autowired
	private LoanApplicationService loanApplicationService;
	
	/*
	 * AIM: post loan application
	 * METHOD: POST
	 * PARAM: RequestBody -> LoanApplication
	 * RESPONSE: LoanApplication
	 * PATH: /api/loanApply/post
	 * */
	@PostMapping("/post")
	public LoanApplication postLoanApplication(@RequestBody LoanApplication loanApplication) {
		return loanApplicationService.postLoanApplication(loanApplication);
	}
	
	/*
	 * AIM: update status by loan id
	 * METHOD: PUT
	 * PARAM: PathVariable -> ID
	 * RESPONSE: LoanApplication
	 * PATH: /api/loanApply/put/status/cancelled/{id}
	 * */
	@PutMapping("/put/status/cancelled/{id}")
	public LoanApplication putLoanApplicationStatusCancelled(@PathVariable int id) {
		return loanApplicationService.putLoanApplicationStatusCancelled(id);
	}
	
	/*
	 * AIM: update loan status
	 * METHOD: PUT
	 * PARAM: PathVariable -> ID | RequestParam -> status, remark
	 * RESPONSE: LoanApplication
	 * PATH: /api/loanApply/put/status/{id}?status=APPROVED&remark=Verified
	 * */
	@PutMapping("/put/status/{id}")
	public LoanApplication putLoanApplicationStatus(@PathVariable int id, @RequestParam String status, @RequestParam String remark) {
		return loanApplicationService.putLoanApplicationStatus(id, status, remark);
	}
	
	/*
	 * AIM: to fetch loan application by status with user login-cred
	 * METHOD: GET
	 * PARAM: Principal | RequestParam -> status
	 * RESPONSE: List<LoanApplication>
	 * PATH: /api/loanApply/get-one/status?status=APPROVED
	 * */
	@GetMapping("/get-one/status")
	public List<LoanApplication> getByStatusAndUsername(Principal principal, @RequestParam String status) {
		String username = principal.getName();
		return loanApplicationService.getByStatusAndUsername(username, status);
	}
	
	/*
	 * AIM: to fetch loan application by status
	 * METHOD: GET
	 * PARAM: RequestParam -> status
	 * RESPONSE: List<LoanApplication>
	 *PATH: /api/loanApply/get-by/status?status=DECLINED
	 **/
	@GetMapping("/get-by/status")
	public List<LoanApplication> getByStatus(@RequestParam String status) {
		return loanApplicationService.getByStatus(status);
	}
	
	/*
	 * AIM: to fetch loan application by branchId
	 * METHOD: GET
	 * PARAM: PathVariable -> branchId
	 * RESPONSE: List<LoanApplication>
	 * PATH: /api/loanApply/get-by/branchId/{branchId}
	 */	
	@GetMapping("get-by/branchId/{branchId}")
	public List<LoanApplication> getByBranchId(@PathVariable String branchId) {
		return loanApplicationService.getByBranchId(branchId);
	}
	
	/*
	 * AIM: fetch all  
	 * METHOD: GET
	 * RESPONSE: List<LoanApplication>
	 * PATH: /api/loanApply/get-all
	 */
	@GetMapping("get-all")
	public List<LoanApplication> getAll() {
		return loanApplicationService.getAll();
	}
	
}
