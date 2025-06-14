package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.model.Beneficiary;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.AccountRepository;
import com.springboot.bankDemo.repository.BeneficiaryRepository;
import com.springboot.bankDemo.repository.BranchRepository;
import com.springboot.bankDemo.repository.CustomerRepository;
import com.springboot.bankDemo.service.BeneficiaryService;

@SpringBootTest
public class BeneficiaryServiceTest {

	@InjectMocks
	private BeneficiaryService beneficiaryService;

	@Mock
	private BeneficiaryRepository beneficiaryRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private BranchRepository branchRepository;

	private Customer customer;
	private Beneficiary beneficiary;
	private Branch branch;
	private User user;
	
	@BeforeEach
	public void init() {

		user = new User();
		user.setId(1);
		user.setUsername("david@gmail.com");
		user.setPassword("david@123");
		user.setRole("CUSTOMER");

		customer = new Customer();
		customer.setId(1);
		customer.setFirstName("David");
		customer.setLastName("Miller");
		customer.setEmail("david@gmail.com");
		customer.setPhoneNumber("9876543210");
		customer.setAddress("Mumbai");
		customer.setRegistrationDate(LocalDate.now());
		customer.setUser(user);

		branch = new Branch();
		branch.setId(1);
		branch.setIfscCode("IFSC0003");
		branch.setBranchName("Coimbatore");
		branch.setAddress("Gandhipuram");
		branch.setEmail("cbe@bank.com");
		branch.setPhoneNumber("9876543211");

		beneficiary = new Beneficiary();
		beneficiary.setId(1);
		beneficiary.setName("Alice");
		beneficiary.setAccountNumber(123456);
		beneficiary.setIfscCode("IFSC0003");
		beneficiary.setBranchName("Coimbatore");
		beneficiary.setDescription("Friend");
	}

	@Test
	public void postBeneficiaryTest() {
		when(customerRepository.getCustomerByUsername("david@gmail.com")).thenReturn(customer);
		when(accountRepository.findById(123456)).thenReturn(Optional.of(new Account()));
		when(branchRepository.getByIfscCode("IFSC0003")).thenReturn(Optional.of(branch));
		when(branchRepository.getBranchByNameIfsc("IFSC0003", "Coimbatore")).thenReturn(Optional.of(branch));
		when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(beneficiary);
		
		assertEquals(beneficiary, beneficiaryService.postBeneficiary("david@gmail.com", beneficiary));
	}

	@Test
	public void putBeneficiaryTest() {
		Beneficiary updated = new Beneficiary();
		updated.setName("Bob");
		updated.setAccountNumber(987654);
		updated.setIfscCode("IFSC5678");
		updated.setBranchName("Mumbai");
		updated.setDescription("Colleague");

		when(beneficiaryRepository.findById(1)).thenReturn(Optional.of(beneficiary));
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(beneficiary);

		Beneficiary result = beneficiaryService.putBeneficiary(1, 1, updated);
		assertEquals("Bob", result.getName());
		assertEquals(987654, result.getAccountNumber());
		assertEquals("IFSC5678", result.getIfscCode());
		assertEquals("Mumbai", result.getBranchName());
		assertEquals("Colleague", result.getDescription());
	}

	@Test
	public void getByCustomerUsernameTest() throws Exception {
		when(customerRepository.getCustomerByUsername("david@gmail.com")).thenReturn(customer);
		when(beneficiaryRepository.getByCustomerId(1)).thenReturn(Optional.of(List.of(beneficiary)));
		assertEquals(List.of(beneficiary), beneficiaryService.getByCustomerUsername("david@gmail.com"));
	}

	@Test
	public void getByIdTest() {
		when(beneficiaryRepository.findById(1)).thenReturn(Optional.of(beneficiary));
		assertEquals(beneficiary, beneficiaryService.getById(1));
	}

	@Test
	public void getAllTest() {
		when(beneficiaryRepository.findAll()).thenReturn(List.of(beneficiary));
		assertEquals(List.of(beneficiary), beneficiaryService.getAll());
	}

	@Test
	public void deleteByIdTest() {
		when(beneficiaryRepository.findById(1)).thenReturn(Optional.of(beneficiary));
		beneficiaryService.deleteById(1);
		verify(beneficiaryRepository, times(1)).deleteById(1);
	}
	
	@AfterEach
	public void afterTest() {
		user = null;
		customer = null;
		branch = null;
		beneficiary = null;
	}
}
