package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.dto.ManagerCreateDto;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Manager;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.ManagerRepository;
import com.springboot.bankDemo.service.BranchService;
import com.springboot.bankDemo.service.ManagerService;
import com.springboot.bankDemo.service.UserService;

@SpringBootTest
public class ManagerExecutiveTest {

	@InjectMocks
	private ManagerService managerService;

	@Mock
	private ManagerRepository managerRepository;

	@Mock
	private BranchService branchService;

	@Mock
	private UserService userService;

	private User user;
	private Branch branch;
	private Manager manager;
	private ManagerCreateDto managerCreateDto;

	@BeforeEach
	public void init() {
		user = new User();
		user.setId(1);
		user.setUsername("manager@gmail.com");
		user.setPassword("password123");
		user.setRole("MANAGER");

		branch = new Branch();
		branch.setId(1);
		branch.setIfscCode("IFSC0002");
		branch.setBranchName("Hyderabad");
		branch.setAddress("Main Road");
		branch.setEmail("hyderabad@bank.com");
		branch.setPhoneNumber("9876543210");

		manager = new Manager();
		manager.setId(1);
		manager.setFirstName("Jane");
		manager.setLastName("Doe");
		manager.setEmail("manager@gmail.com");
		manager.setPhoneNumber("8888888888");
		manager.setAddress("Hyderabad");
		manager.setBranch(branch);
		manager.setUser(user);

		managerCreateDto = new ManagerCreateDto();
		managerCreateDto.setFirstName("Jane");
		managerCreateDto.setLastName("Doe");
		managerCreateDto.setEmail("manager@gmail.com");
		managerCreateDto.setPhoneNumber("8888888888");
		managerCreateDto.setAddress("Hyderabad");
		managerCreateDto.setUsername("manager@gmail.com");
		managerCreateDto.setPassword("password123");
	}

	@Test
	public void postManagerTest() {
		when(branchService.getById(1)).thenReturn(branch);
		when(userService.signUp(any(User.class))).thenReturn(user);
		when(managerRepository.save(any(Manager.class))).thenReturn(manager);
		assertEquals(manager, managerService.postManager(1, managerCreateDto));
	}

	@Test
	public void getByUsernameTest() {
		when(managerRepository.getManagerByUsername("manager@gmail.com")).thenReturn(manager);
		assertEquals(manager, managerService.getByUsername("manager@gmail.com"));
	}

	@Test
	public void getAllTest() {
		List<Manager> list = List.of(manager);
		when(managerRepository.findAll()).thenReturn(list);
		assertEquals(list, managerService.getAll());
	}
	
	@Test
	public void putManagerTest() {
		Manager updated = new Manager();
		updated.setFirstName("Janet");
		updated.setLastName("Smith");
		updated.setEmail("janet@gmail.com");
		updated.setPhoneNumber("9999999999");
		updated.setAddress("Delhi");
		
		when(managerRepository.getManagerByUsername("manager@gmail.com")).thenReturn(manager);
		when(managerRepository.save(any(Manager.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		Manager result = managerService.putManager("manager@gmail.com", updated);
		assertEquals("Janet", result.getFirstName());
		assertEquals("Smith", result.getLastName());
		assertEquals("janet@gmail.com", result.getEmail());
		assertEquals("9999999999", result.getPhoneNumber());
		assertEquals("Delhi", result.getAddress());
	}

	@AfterEach
	public void afterTest() {
		user = null;
		manager = null;
		managerCreateDto = null;
		branch = null;
	}
}
