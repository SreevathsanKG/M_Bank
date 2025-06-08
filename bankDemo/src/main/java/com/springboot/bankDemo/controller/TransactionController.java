package com.springboot.bankDemo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bankDemo.dto.AccountStatementDto;
import com.springboot.bankDemo.dto.TransactionDto;
import com.springboot.bankDemo.dto.TransactionListDto;
import com.springboot.bankDemo.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	/*
	 * AIM: post deposit transaction 
	 * PARAM: PathVariable -> accountId | RequestBody -> TransactionDto
	 * METHOD: POST
	 * RESPONSE: void
	 * PATH: /api/transaction/post/deposit/{accountId}
	 * */
	@PostMapping("/post/deposit/{accountId}")
	public void postDeposite(@PathVariable int accountId, @RequestBody TransactionDto transactionDto) {
		transactionService.postDeposit(accountId, transactionDto);
	}
	
	/*
	 * AIM: post withdraw transaction 
	 * PARAM: PathVariable -> accountId | RequestBody -> TransactionDto
	 * METHOD: POST
	 * RESPONSE: void
	 * PATH: /api/transaction/post/withdraw/{accountId}
	 * */
	@PostMapping("/post/withdraw/{accountId}")
	public void postWithdraw(@PathVariable int accountId, @RequestBody TransactionDto transactionDto) {
		transactionService.postWithdraw(accountId, transactionDto);
	}
	
	/*
	 * AIM: post transfer transaction 
	 * PARAM: PathVariable -> accountId, beneficiaryId | RequestParam -> transferType| RequestBody -> TransactionDto
	 * METHOD: POST
	 * RESPONSE: void
	 * PATH: /api/transaction/post/transfer/{accountId}/{beneficiaryId}?transferType=NEFT
	 * */
	@PostMapping("/post/transfer/{accountId}/{beneficiaryId}")
	public void postTransfer(@PathVariable int accountId, @PathVariable int beneficiaryId, @RequestParam String transferType, @RequestBody TransactionDto transactionDto) {
		transactionService.postTransfer(accountId, beneficiaryId, transferType, transactionDto);
	}
	
	/*
	 * AIM: get transaction by accountId between given dates
	 * PARAM: PathVariable -> accountId | RequestParam -> fromDate, tillDate, page, size
	 * METHOD: GET
	 * RESPONSE: List<TransactionListDto>
	 * PATH: /api/transaction/get-btw/{accountId}?fromDate=2025-06-03&tillDate=2025-06-06&page=0&size=10
	 * */
	@GetMapping("/get-btw/{accountId}")
	public List<TransactionListDto> getTxnBtwDateByAccId(@PathVariable int accountId,@RequestParam LocalDate fromDate,@RequestParam LocalDate tillDate,
			@RequestParam(required = false, defaultValue = "0") int page,@RequestParam(required = false, defaultValue = "10000") int size) {
		return transactionService.getTxnBtwDateByAccId(accountId, fromDate, tillDate, page, size);
	}
	
	/*
	 * AIM: get transaction by accountId from given date
	 * PARAM: PathVariable -> accountId | RequestParam -> fromDate, page, size
	 * METHOD: GET
	 * RESPONSE: List<TransactionListDto>
	 * PATH: /api/transaction/get-from/{accountId}?fromDate=2025-06-03&page=0&size=10
	 * */
	@GetMapping("/get-from/{accountId}")
	public List<TransactionListDto> getTxnFromDateByAccId(@PathVariable int accountId,@RequestParam LocalDate fromDate,
			@RequestParam(required = false, defaultValue = "0") int page,@RequestParam(required = false, defaultValue = "10000") int size) {
		return transactionService.getTxnFromDateByAccId(accountId, fromDate, page, size);
	}
	
	/*
	 * AIM: get last 10 transaction
	 * PARAM: PathVariable -> accountId
	 * METHOD: GET
	 * RESPONSE: List<TransactionListDto>
	 * PATH: /api/transaction/get-10/{accountId}
	 * */
	@GetMapping("/get-10/{accountId}")
	public List<TransactionListDto> getLast10TxnByAccId(@PathVariable int accountId) {
		return transactionService.getLast10TxnByAccId(accountId);
	}
	
	/*
	 * AIM: get transaction by accountId for last N month
	 * PARAM: PathVariable -> accountId | RequestParam -> nMonth, page, size
	 * METHOD: GET
	 * RESPONSE: List<TransactionListDto>
	 * PATH: /api/transaction/get-nMonth/{accountId}?nMonth=1&page=0&size=10
	 * */
	@GetMapping("/get-nMonth/{accountId}")
	public List<TransactionListDto> getTxnLastNMonthByAccId(@PathVariable int accountId,@RequestParam int nMonth,
			@RequestParam(required = false, defaultValue = "0") int page,@RequestParam(required = false, defaultValue = "10000") int size) {
		return transactionService.getTxnLastNMonthByAccId(accountId, nMonth, page, size);
	}
	
	/*
	 * AIM: get ACCOUNT STATEMENT
	 * PARAM: PathVariable -> accountId | RequestParam -> fromDate, tillDate
	 * METHOD: GET
	 * RESPONSE: List<AccountStatement>
	 * PATH: /api/transaction/get/statement/{accountId}?nfromDate=2025-06-07&tillDate=2025-06-09
	 * */
	@GetMapping("/get/statement/{accountId}")
	public AccountStatementDto getAccStmtBtwDateByAccId(@PathVariable int accountId, @RequestParam LocalDate fromDate, @RequestParam LocalDate tillDate) {
		return transactionService.getAccStmtBtwDatebyAccId(accountId, fromDate, tillDate);
	}
}
