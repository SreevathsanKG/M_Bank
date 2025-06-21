package com.springboot.bankDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.bankDemo.enums.LoanType;
import com.springboot.bankDemo.model.LoanDetails;
import com.springboot.bankDemo.repository.LoanDetailsRepository;
import com.springboot.bankDemo.service.LoanDetailsSevice;

@SpringBootTest
public class LoanDetailsServiceTest {

	@InjectMocks
    private LoanDetailsSevice loanDetailsService;
    @Mock
    private LoanDetailsRepository loanDetailsRepository;

    private LoanDetails loanDetails;

    @BeforeEach
    public void init() {
        loanDetails = new LoanDetails();
        loanDetails.setId(1);
        loanDetails.setLoanType(LoanType.HOME);
        loanDetails.setPrincipalAmount(new BigDecimal("100000"));
        loanDetails.setInterestRate(new BigDecimal("0.10")); // 10%
        loanDetails.setTermInMonth(12);
        loanDetails.setTotalRepayableAmount(BigDecimal.valueOf(110000.00));
        loanDetails.setEmiAmount(BigDecimal.valueOf(8333.33));
    }

    @Test
    public void postLoanDetailsTest() {
        when(loanDetailsRepository.save(any(LoanDetails.class))).thenReturn(loanDetails);
        assertEquals(loanDetails, loanDetailsService.postLoanDetails(loanDetails));
    }

    @Test
    public void getByIdTest() {
        when(loanDetailsRepository.findById(1)).thenReturn(Optional.of(loanDetails));
        assertEquals(loanDetails, loanDetailsService.getById(1));
    }

    @Test
    public void getByIdNotFoundTest() {
        when(loanDetailsRepository.findById(2)).thenReturn(Optional.empty());
        RuntimeException e = assertThrows(RuntimeException.class, () -> {
            loanDetailsService.getById(2);
        });
        assertEquals("ID is Invalid", e.getMessage());
    }

    @Test
    public void getAllTest() {
        when(loanDetailsRepository.findAll()).thenReturn(List.of(loanDetails));
        assertEquals(List.of(loanDetails), loanDetailsService.getAll());
    }
    
    @AfterEach
    public void afterTest() {
    	loanDetails = null;
    }
}
