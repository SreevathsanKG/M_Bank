package com.springboot.bankDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Manager;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.ManagerRepository;

@Service
public class ManagerService {

	private ManagerRepository managerRepository;
	private BranchService branchService;
	private UserService userService;

	public ManagerService(ManagerRepository managerRepository, BranchService branchService, UserService userService) {
		this.managerRepository = managerRepository;
		this.branchService = branchService;
		this.userService = userService;
	}

	// add manager
	public Manager postManager(int branchId, Manager manager) {
		Branch branch = branchService.getById(branchId);
		manager.setBranch(branch);
		User user = manager.getUser();
		user.setRole("MANAGER");
		user = userService.signUp(user);
		manager.setUser(user);
		return managerRepository.save(manager);
	}

	// update manager data
	public Manager putManager(String username, Manager manager) {
		Manager dbManager = managerRepository.getManagerByUsername(username);
		if (manager.getFirstName() != null)
			dbManager.setFirstName(manager.getFirstName());
		if (manager.getLastName() != null)
			dbManager.setLastName(manager.getLastName());
		if (manager.getEmail() != null)
			dbManager.setEmail(manager.getEmail());
		if (manager.getPhoneNumber() != null)
			dbManager.setPhoneNumber(manager.getPhoneNumber());
		if (manager.getAddress() != null)
			dbManager.setAddress(manager.getAddress());
		return managerRepository.save(dbManager);
	}

	// get manager by username
	public Manager getByUsername(String username) {
		return managerRepository.getManagerByUsername(username);
	}

	// get all manager
	public List<Manager> getAll() {
		return managerRepository.findAll();
	}
}
