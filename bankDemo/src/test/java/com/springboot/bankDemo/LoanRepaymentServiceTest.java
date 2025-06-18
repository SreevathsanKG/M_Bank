package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.enums.LoanStatus;
import com.springboot.bankDemo.enums.LoanType;
import com.springboot.bankDemo.model.Loan;
import com.springboot.bankDemo.model.LoanRepayment;
import com.springboot.bankDemo.repository.LoanRepaymentRepository;
import com.springboot.bankDemo.repository.LoanRepository;
import com.springboot.bankDemo.service.LoanRepaymentService;
import com.springboot.bankDemo.service.LoanService;

@SpringBootTest
public class LoanRepaymentServiceTest {

	@InjectMocks
	private LoanRepaymentService loanRepaymentService;
	@Mock
	private LoanRepaymentRepository loanRepaymentRepository;
	@Mock
	private LoanService loanService;
	@Mock
	private LoanRepository loanRepository;

	private Loan loan;
	private LoanRepayment loanRepayment;

	@BeforeEach
	public void setup() {
		loan = new Loan();
		loan.setId(1);
//		loan.setPrincipalAmount(new BigDecimal("100000"));
		loan.setBalanceAmount(new BigDecimal("100000"));
//		loan.setLoanType(LoanType.HOME);
		loan.setStatus(LoanStatus.ACTIVE);
//		loan.setInterestRate(new BigDecimal("0.10"));
//		loan.setTermInMonth(12);
//		loan.setEmiAmount(new BigDecimal("8333.33"));
		loan.setStartDate(LocalDate.of(2025, 1, 1));
		loan.setEndDate(LocalDate.of(2026, 1, 1));

		loanRepayment = new LoanRepayment();
		loanRepayment.setId(1);
		loanRepayment.setRepaymentAmount(new BigDecimal("20000"));
		loanRepayment.setRepaymentDate(LocalDate.now());
		loanRepayment.setLoan(loan);
	}

	@Test
	public void postLoanRepaymentTest() {
		when(loanRepository.findById(1)).thenReturn(Optional.of(loan));
		when(loanService.putLoanBalance(1, new BigDecimal("20000"))).thenReturn(loan);
		when(loanRepaymentRepository.save(any(LoanRepayment.class))).thenReturn(loanRepayment);
		assertEquals(loanRepayment, loanRepaymentService.postLoanRepayment(1, new BigDecimal("20000")));
	}

	@Test
	public void getByLoanIdTest() {
		when(loanRepaymentRepository.getByLoanId(1)).thenReturn(List.of(loanRepayment));
		assertEquals(List.of(loanRepayment), loanRepaymentService.getByLoanId(1));
	}
}
