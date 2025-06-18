package com.springboot.bankDemo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.bankDemo.enums.LoanApplicationStatus;
import com.springboot.bankDemo.enums.LoanStatus;
import com.springboot.bankDemo.enums.LoanType;
import com.springboot.bankDemo.enums.TransferType;

@Service
public class EnumsService {
	
	// fetch loan application status from enum
	public List<String> getEnumLoanApplicationStatus() {
		List<String> status = Arrays.stream(LoanApplicationStatus.values()).map(la -> la.name()).toList();
		return status;
	}
	
	// fetch loan status from enum
	public List<String> getEnumLoanStatus() {
		List<String> status = Arrays.stream(LoanStatus.values()).map(l -> l.name()).toList();
		return status;
	}
	
	// fetch loan type status from enum
	public List<String> getEnumLoanType() {
		List<String> type = Arrays.stream(LoanType.values()).map(t -> t.name()).toList();
		return type;
	}
	
	// fetch transfer type from enum
	public List<String> getEnumTransferType() {
		List<String> transferType = Arrays.stream(TransferType.values()).map(t -> t.name()).toList();
		return transferType;
	}
}
