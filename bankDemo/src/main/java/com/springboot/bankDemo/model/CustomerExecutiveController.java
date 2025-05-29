package com.springboot.bankDemo.model;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.service.CustomerExecutiveService;

@RestController
@RequestMapping("/api/ce")
public class CustomerExecutiveController {

	@Autowired
	private CustomerExecutiveService customerExecutiveService;
	
	/*
	 * AIM: to insert customer-executive by branch id
	 * METHOD: POST 
	 * PARAM: CustomerExecutive -> RequestBody, Pathvariable branch id
	 * RESPONSE: CE 
	 * PATH: /api/ce/post/{branachId}
	 * ACCESS: MANAGER, GM
	 */
	@PostMapping("/post/{branchId}")
	public CustomerExecutive postCustomerExecutive(@PathVariable int branchId, @RequestBody CustomerExecutive customerExecutive) {
		return customerExecutiveService.postCustomerExecutive(branchId, customerExecutive);
	}
	
	/*
	 * AIM: update CE by loggedIn credentials
	 * METHOD: PUT
	 * PARAM: CE -> RequestBody, Principal -> CE-username
	 * RESPONSE: CE 
	 * PATH: /api/ce/put
	 * ACCESS: CE
	 */
	@PutMapping("/put")
	public CustomerExecutive putCustomerExecutive(Principal principal, @RequestBody CustomerExecutive customerExecutive) {
		String username = principal.getName();
		return customerExecutiveService.putCustomerExecutive(username, customerExecutive);
	}
	
	/*
	 * AIM: get ce data by logIn cred
	 * METHOD: GET
	 * PARAM: Principal -> CE-username
	 * RESPONSE: CE 
	 * PATH: /api/ce/get-one
	 * ACCESS: CE
	 */
	@GetMapping("/get-one")
	public CustomerExecutive getByUsername(Principal principal) {
		String username = principal.getName();
		return customerExecutiveService.getByUsername(username);
	}
	
	/*
	 * AIM: get all CE
	 * METHOD: GET
	 * RESPONSE: List<CE> 
	 * PATH: /api/ce/get-all
	 * ACCESS: GM
	 */
	@GetMapping("/get-all")
	public List<CustomerExecutive> getAll() {
		return customerExecutiveService.getAll();
	}
	
	/*
	 * AIM: get CE by branchId
	 * METHOD: GET
	 * PARAM: branchId ->PathVariable
	 * RESPONSE: List<CE> 
	 * PATH: /api/ce/get/{branchId}
	 * ACCESS: MANAGER, GM
	 */
	@GetMapping("/get/{branchId}")
	public List<CustomerExecutive> getByBranch(@PathVariable int branchId) {
		return customerExecutiveService.getByBranch(branchId);
	}
}
