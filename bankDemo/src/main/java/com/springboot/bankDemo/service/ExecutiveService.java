package com.springboot.bankDemo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.bankDemo.enums.ExecutiveRole;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Executive;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.ExecutiveRepository;

@Service
public class ExecutiveService {

	private ExecutiveRepository executiveRepository;
	private BranchService branchService;
	private UserService userService;

	public ExecutiveService(ExecutiveRepository executiveRepository,
			BranchService branchService, UserService userService) {
		this.executiveRepository = executiveRepository;
		this.branchService = branchService;
		this.userService = userService;
	}

	// add customer-executive
	public Executive postExecutive(int branchId, String role, Executive executive) {
		ExecutiveRole.valueOf(role);
		Branch branch = branchService.getById(branchId);
		executive.setBranch(branch);
		User user = executive.getUser();
		user.setRole(role);
		user = userService.signUp(user);
		executive.setUser(user);
		return executiveRepository.save(executive);
	}

	// update customer-executive data
	public Executive putExecutive(String username, Executive executive) {
		Executive dbExecutive = executiveRepository.getExecutiveByUsername(username);
		if (executive.getFirstName() != null)
			dbExecutive.setFirstName(executive.getFirstName());
		if (executive.getLastName() != null)
			dbExecutive.setLastName(executive.getLastName());
		if (executive.getEmail() != null)
			dbExecutive.setEmail(executive.getEmail());
		if (executive.getPhoneNumber() != null)
			dbExecutive.setPhoneNumber(executive.getPhoneNumber());
		if (executive.getAddress() != null)
			dbExecutive.setAddress(executive.getAddress());
		return executiveRepository.save(dbExecutive);
	}

	// get customer executive by username
	public Executive getByUsername(String username) {
		return executiveRepository.getExecutiveByUsername(username);
	}

	// get customer executive under a branch
	public List<Executive> getByBranch(int branchId) {
		return executiveRepository.getExecutiveByBranch(branchId);
	}
	// fetch all executives
	public List<Executive> getAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return executiveRepository.findAll(pageable).getContent();
	}
	
	// fetch all roles in enum
	public List<String> getRoles() {
		List<String> roles = Arrays.stream(ExecutiveRole.values()).map(r -> r.name()).toList();
		return roles;
	}
}
