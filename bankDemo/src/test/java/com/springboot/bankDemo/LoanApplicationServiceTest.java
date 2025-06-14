package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.enums.LoanApplicationStatus;
import com.springboot.bankDemo.enums.LoanType;
import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.model.AccountType;
import com.springboot.bankDemo.model.Branch;
import com.springboot.bankDemo.model.Customer;
import com.springboot.bankDemo.model.LoanApplication;
import com.springboot.bankDemo.model.User;
import com.springboot.bankDemo.repository.LoanApplicationRepository;
import com.springboot.bankDemo.service.LoanApplicationService;

@SpringBootTest
public class LoanApplicationServiceTest {

	@InjectMocks
	private LoanApplicationService loanApplicationService;
	@Mock
	private LoanApplicationRepository loanApplicationRepository;

	private User user;
	private Customer customer;
	private Branch branch;
	private AccountType accountType;
	private Account account;
	private LoanApplication loanApplication;

	@BeforeEach
	public void setup() {
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

		accountType = new AccountType();
		accountType.setId(1);
		accountType.setType("SAVINGS");
		accountType.setInitialDeposit(new BigDecimal("5000.00"));

		account = new Account();
		account.setId(1);
		account.setCustomer(customer);
		account.setBranch(branch);
		account.setAccountType(accountType);
		account.setBalance(accountType.getInitialDeposit());
		account.setOpenDate(LocalDate.now());
		account.setStatus("PENDING_APPROVAL");
		account.setPanNumber("ABCDE1234F");
		account.setAadharNumber("123456789012");

		loanApplication = new LoanApplication();
		loanApplication.setId(1);
		loanApplication.setRequiredLoanAmount(new BigDecimal("100000.00"));
		loanApplication.setLoanType(LoanType.HOME);
		loanApplication.setStatus(LoanApplicationStatus.PENDING);
		loanApplication.setDocument("docLink");
		loanApplication.setApplicationDate(LocalDate.now());
		loanApplication.setAccount(account);
	}

	@Test
	public void testPostLoanApplication_Success() {
		when(loanApplicationRepository.getLoanAppExistsByCustomerAndType(customer, LoanType.HOME)).thenReturn(false);
		when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);
		assertEquals(loanApplication, loanApplicationService.postLoanApplication(loanApplication));

		when(loanApplicationRepository.getLoanAppExistsByCustomerAndType(customer, LoanType.HOME)).thenReturn(true);
		RuntimeException e = assertThrows(RuntimeException.class,
				() -> loanApplicationService.postLoanApplication(loanApplication));
		assertEquals("Customer has already applied or has the Loan of this type".toLowerCase(), e.getMessage().toLowerCase());
	}

	@Test
	public void testPutLoanApplicationStatusCancelled() {
		when(loanApplicationRepository.findById(1)).thenReturn(Optional.of(loanApplication));
		loanApplication.setStatus(LoanApplicationStatus.CANCELLED);
		when(loanApplicationRepository.save(loanApplication)).thenReturn(loanApplication);
		assertEquals(loanApplication, loanApplicationService.putLoanApplicationStatusCancelled(1));
	}

	@Test
	public void testPutLoanApplicationStatus() {
		when(loanApplicationRepository.findById(1)).thenReturn(Optional.of(loanApplication));
		loanApplication.setStatus(LoanApplicationStatus.APPROVED);
		loanApplication.setRemark("Verified");
		when(loanApplicationRepository.save(loanApplication)).thenReturn(loanApplication);
		assertEquals(loanApplication, loanApplicationService.putLoanApplicationStatus(1, "APPROVED", "Verified"));
	}

	@Test
	public void testGetByStatusAndUsername() {
		when(loanApplicationRepository.getByStatusAndUsername("david@gmail.com", "PENDING"))
				.thenReturn(List.of(loanApplication));
		assertEquals(List.of(loanApplication), loanApplicationService.getByStatusAndUsername("david@gmail.com", "PENDING"));
	}

	@Test
	public void testGetByStatus() {
		when(loanApplicationRepository.getByStatus("PENDING")).thenReturn(List.of(loanApplication));
		assertEquals(List.of(loanApplication), loanApplicationService.getByStatus("PENDING"));
	}

	@Test
	public void testGetByBranchId() {
		when(loanApplicationRepository.getByBranchId("IFSC0003")).thenReturn(List.of(loanApplication));
		assertEquals(List.of(loanApplication), loanApplicationService.getByBranchId("IFSC0003"));
	}

	@Test
	public void testGetAll() {
		when(loanApplicationRepository.findAll()).thenReturn(List.of(loanApplication));
		assertEquals(List.of(loanApplication), loanApplicationService.getAll());
	}

	@AfterEach
	public void afterTest() {
		user = null;
		customer = null;
		branch = null;
		accountType = null;
		account = null;
		loanApplication = null;
	}
}
