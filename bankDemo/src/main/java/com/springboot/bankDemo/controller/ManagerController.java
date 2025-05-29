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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.model.Manager;
import com.springboot.bankDemo.service.ManagerService;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	/*
	 * AIM: manager inserted by GM
	 * METHOD: POST 
	 * PARAM: Manager -> RequestBody, branchId -> Pathvariable 
	 * RESPONSE: Manager 
	 * PATH: /api/anager/post/{branchId}
	 * ACCESS: GM
	 */
	@PostMapping("/post/{branchId}")
	public Manager postManager(@PathVariable int branchId, @RequestBody Manager manager) {
		return managerService.postManager(branchId, manager);
	}
	
	/*
	 * AIM: update manager data 
	 * METHOD: PUT 
	 * PARAM: Manager -> RequestBody, Username -> Principal
	 * RESPONSE: manager 
	 * PATH: /api/manager/put
	 */
	@PutMapping("/put")
	public Manager putManager(Principal principal, @RequestBody Manager manager) {
		String username = principal.getName();
		return managerService.putManager(username, manager);
	}
	
	/*
	 * AIM: get manager ny username 
	 * METHOD: GET
	 * PARAM: Username ->Principal
	 * RESPONSE: Manager 
	 * PATH: /api/manager/get-one
	 */
	@GetMapping("/get-one")
	public Manager getbyUsername(Principal principal) {
		String username = principal.getName();
		return managerService.getByUsername(username);
	}
	
	/*
	 * AIM: fetch all manager
	 * METHOD: GET
	 * RESPONSE: Manager 
	 * PATH: /api/manager/get-all
	 */
	@GetMapping("/get-all")
	public List<Manager> getAll() {
		return managerService.getAll();
	}
}
