package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.bankDemo.dto.AccountStatementDto;
import com.springboot.bankDemo.dto.StatementListDto;
import com.springboot.bankDemo.dto.TransactionDto;
import com.springboot.bankDemo.dto.TransactionListDto;
import com.springboot.bankDemo.enums.EntryType;
import com.springboot.bankDemo.enums.TransferType;
import com.springboot.bankDemo.model.Account;
import com.springboot.bankDemo.model.Beneficiary;
import com.springboot.bankDemo.model.Transaction;
import com.springboot.bankDemo.repository.AccountRepository;
import com.springboot.bankDemo.repository.BeneficiaryRepository;
import com.springboot.bankDemo.repository.TransactionRepository;

@Service
public class TransactionService {

	public TransactionRepository transactionRepository;
	public AccountRepository accountRepository;
	public BeneficiaryRepository beneficiaryRepository;

	private Transaction transaction;

	public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository,
			BeneficiaryRepository beneficiaryRepository) {
		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.beneficiaryRepository = beneficiaryRepository;
	}

	// deposit
	public Transaction postDeposit(int accountId, TransactionDto transactionDto) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		account.setBalance(account.getBalance().add(amount));
		accountRepository.save(account);
		transaction = returnSetTransaction("DEPOSIT", amount, account, EntryType.CREDIT,
				transactionDto.getDescription(), account);
		return transactionRepository.save(transaction);
	}

	// withdraw
	public Transaction postWithdraw(int accountId, TransactionDto transactionDto) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		if (account.getBalance().compareTo(amount) < 0)
			throw new RuntimeException("Insufficient balance in the account");
		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);
		transaction = returnSetTransaction("WITHDRAW", amount, account, EntryType.DEBIT,
				transactionDto.getDescription(), account);
		return transactionRepository.save(transaction);
	}
	
	// loan withdraw
	public Transaction postLoanWithdraw(int accountId, TransactionDto transactionDto) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		if (account.getBalance().compareTo(amount) < 0)
			throw new RuntimeException("Insufficient balance in the account");
		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);
		transaction = returnSetTransaction("LOAN", amount, account, EntryType.DEBIT,
				transactionDto.getDescription(), account);
		return transactionRepository.save(transaction);
	}

	// transfer
	public Transaction postTransfer(int accountId, int beneficiaryId, String transferType, TransactionDto transactionDto) {
		Account fromAccount = accountRepository.findById(accountId) // fetch fromAccount
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		TransferType type = TransferType.valueOf(transferType); // fetch transfer type from enum
		BigDecimal charge = type.getCharge(); // fetch charge for transfer type
		BigDecimal totalDebit = amount.add(charge);
		if (fromAccount.getBalance().compareTo(totalDebit) < 0)
			throw new RuntimeException("Insufficient balance in the account");
		Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId) // fetch beneficiary
				.orElseThrow(() -> new RuntimeException("Beneficiary ID is Invalid"));
		Account toAccount = accountRepository.findById(beneficiary.getAccountNumber()) // fetch toAccount
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		// from-account and from-transaction setup
		fromAccount.setBalance(fromAccount.getBalance().subtract( // subtract amount+charge in fromAccount
				totalDebit));
		accountRepository.save(fromAccount); // save fromAccount
		Transaction fromTransaction = returnSetTransaction(transferType, totalDebit, toAccount, EntryType.DEBIT,
				transactionDto.getDescription(), fromAccount);
		transactionRepository.save(fromTransaction);
		// to-account and to-transaction setup
		toAccount.setBalance(toAccount.getBalance().add(amount)); // add amount in toAccount
		accountRepository.save(toAccount); // save toAccount
		Transaction toTransaction = returnSetTransaction(transferType, amount, fromAccount, EntryType.CREDIT,
				transactionDto.getDescription(), toAccount);
		return transactionRepository.save(toTransaction);
	}

	// fetch transaction by accountId between given date
	public List<TransactionListDto> getTxnBtwDateByAccId(int accountId, LocalDate fromDate, LocalDate tillDate,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Transaction> list = transactionRepository.getTxnBtwDateByAccId(accountId, fromDate, tillDate, pageable);
		return returnTransactionListDto(list);
	}

	// fetch transaction by accountId from given date
	public List<TransactionListDto> getTxnFromDateByAccId(int accountId, LocalDate fromDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Transaction> list = transactionRepository.getTxnFromDateByAccId(accountId, fromDate, pageable);
		return returnTransactionListDto(list);
	}

	// fetch last 10 transaction
	public List<TransactionListDto> getLast10TxnByAccId(int accountId) {
		List<Transaction> list = transactionRepository.getLast10TxnByAccId(accountId, PageRequest.of(0, 10));
		return returnTransactionListDto(list);
	}

	// fetch transaction by given N month
	public List<TransactionListDto> getTxnLastNMonthByAccId(int acountId, int nMonth, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDate fromDate = LocalDate.now().minusMonths(nMonth);
		List<Transaction> list = transactionRepository.getTxnFromDateByAccId(acountId, fromDate, pageable);
		return returnTransactionListDto(list);
	}

	// fetch account statement
	public AccountStatementDto getAccStmtBtwDatebyAccId(int accountId, LocalDate fromDate, LocalDate toDate) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		List<Transaction> list = transactionRepository.getTxnBtwDateByAccId(accountId, fromDate, toDate);
		List<StatementListDto> stmtList = returnStatementListDto(list);
		BigDecimal openingBalance = list.isEmpty() ? BigDecimal.ZERO : list.get(0).getBalanceAfterTxn();
		BigDecimal closingBalance = list.isEmpty() ? BigDecimal.ZERO : list.get(list.size() - 1).getBalanceAfterTxn();
		return new AccountStatementDto(account.getId(), account.getCustomer().getFirstName(), fromDate, toDate,
				openingBalance, closingBalance, stmtList);
	}

	// fetch transaction between given date
	public List<Transaction> getTransactionBetweenDate(LocalDate fromDate, LocalDate tillDate) {
		return transactionRepository.getTransactionBetweenDate(fromDate, tillDate);
	}

	// fetch transaction from a given date to till now
	public List<Transaction> getTransactionsFromDate(LocalDate fromDate) {
		return transactionRepository.getTransactionFromDate(fromDate);
	}

	// fetch all
	public List<Transaction> getAll() {
		return transactionRepository.findAll();
	}

	// return setTransaction
	public Transaction returnSetTransaction(String txnType, BigDecimal amount, Account transferAccount, EntryType type,
			String description, Account account) {
		transaction = new Transaction();
		transaction.setTransactionType(txnType);
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setTransferAccountId(transferAccount.getId());
		transaction.setEntryType(type);
		if (description != null)
			transaction.setDescription(description);
		transaction.setBalanceAfterTxn(account.getBalance());
		transaction.setAccount(account);
		return transaction;
	}

	// return for TransactionListDto
	public List<TransactionListDto> returnTransactionListDto(List<Transaction> list) {
		return list.stream().map(t -> {
			TransactionListDto dto = new TransactionListDto();
			dto.setTransactionType(t.getTransactionType() );
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setTransferAccountId(t.getTransferAccountId());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}

	// return for AccountStatementDto
	public List<StatementListDto> returnStatementListDto(List<Transaction> list) {
		return list.stream().map(t -> {
			StatementListDto dto = new StatementListDto();
			dto.setTransactionType(t.getTransactionType());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}
}
