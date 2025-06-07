package com.springboot.bankDemo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public void postDeposit(int accountId, TransactionDto transactionDto) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		account.setBalance(account.getBalance().add(amount));
		accountRepository.save(account);
		transaction = new Transaction();
		transaction.setTransactionType("DEPOSIT");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setTransferAccountId(account.getId());
		transaction.setEntryType(EntryType.CREDIT);
		if (transactionDto.getDescription() != null)
			transaction.setDescription(transactionDto.getDescription());
		transaction.setBalanceAfterTxn(account.getBalance());
		transaction.setAccount(account);
		transactionRepository.save(transaction);
	}

	// withdraw
	public void postWithdraw(int accountId, TransactionDto transactionDto) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("ID is Invalid"));
		BigDecimal amount = transactionDto.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new RuntimeException("Amount cannot be less than or equal to Zero");
		if (account.getBalance().compareTo(amount) < 0)
			throw new RuntimeException("Insufficient balance in the account");
		account.setBalance(account.getBalance().subtract(amount));
		accountRepository.save(account);
		transaction = new Transaction();
		transaction.setTransactionType("WITHDRAW");
		transaction.setTransactionDate(LocalDate.now());
		transaction.setAmount(amount);
		transaction.setTransferAccountId(account.getId());
		transaction.setEntryType(EntryType.DEBIT);
		if (transactionDto.getDescription() != null)
			transaction.setDescription(transactionDto.getDescription());
		transaction.setBalanceAfterTxn(account.getBalance());
		transaction.setAccount(account);
		transactionRepository.save(transaction);
	}

	// transfer
	public void postTransfer(int accountId, int beneficiaryId, String transferType, TransactionDto transactionDto) {
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
		Transaction fromTransaction = new Transaction();
		fromTransaction.setTransactionType(type.name()); // set transaction
		fromTransaction.setTransactionDate(LocalDate.now());
		fromTransaction.setAmount(totalDebit);
		fromTransaction.setTransferAccountId(toAccount.getId());
		fromTransaction.setEntryType(EntryType.DEBIT);
		if (transactionDto.getDescription() != null)
			fromTransaction.setDescription(transactionDto.getDescription());
		fromTransaction.setBalanceAfterTxn(fromAccount.getBalance());
		fromTransaction.setAccount(fromAccount);
		transactionRepository.save(fromTransaction);
		// to-account and to-transaction setup
		toAccount.setBalance(toAccount.getBalance().add(amount)); // add amount in toAccount
		accountRepository.save(toAccount); // save toAccount
		Transaction toTransaction = new Transaction();
		toTransaction.setTransactionType(type.name()); // set transaction
		toTransaction.setTransactionDate(LocalDate.now());
		toTransaction.setAmount(amount);
		toTransaction.setTransferAccountId(fromAccount.getId());
		toTransaction.setEntryType(EntryType.CREDIT);
		if (transactionDto.getDescription() != null)
			toTransaction.setDescription(transactionDto.getDescription());
		toTransaction.setBalanceAfterTxn(toAccount.getBalance());
		toTransaction.setAccount(toAccount);
		transactionRepository.save(toTransaction);
	}

	// fetch transaction by accountId between given date
	public List<TransactionListDto> getTxnBtwDateByAccId(int accountId, LocalDate fromDate, LocalDate tillDate, int page,
			int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Transaction> list = transactionRepository.getTxnBtwDateByAccId(accountId, fromDate, tillDate, pageable);
		return list.stream().map(t -> {
			TransactionListDto dto = new TransactionListDto();
			dto.setTransactionType(t.getTransactionType());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setTransferAccountId(t.getTransferAccountId());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}

	// fetch transaction by accountId from given date
	public List<TransactionListDto> getTxnFromDateByAccId(int accountId, LocalDate fromDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<Transaction> list = transactionRepository.getTxnFromDateByAccId(accountId, fromDate, pageable);
		return list.stream().map(t -> {
			TransactionListDto dto = new TransactionListDto();
			dto.setTransactionType(t.getTransactionType());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setTransferAccountId(t.getTransferAccountId());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}

	// fetch last 10 transaction
	public List<TransactionListDto> getLast10TxnByAccId(int accountId) {
		List<Transaction> list = transactionRepository.getLast10TxnByAccId(accountId, PageRequest.of(0, 10));
		return list.stream().map(t -> {
			TransactionListDto dto = new TransactionListDto();
			dto.setTransactionType(t.getTransactionType());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setTransferAccountId(t.getTransferAccountId());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}

	// fetch transaction by given last month
	public List<TransactionListDto> getTxnLastNMonthByAccId(int acountId, int nMonth, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		LocalDate fromDate = LocalDate.now().minusMonths(nMonth);
		List<Transaction> list = transactionRepository.getTxnFromDateByAccId(acountId, fromDate, pageable);
		return list.stream().map(t -> {
			TransactionListDto dto = new TransactionListDto();
			dto.setTransactionType(t.getTransactionType());
			dto.setTransactionDate(t.getTransactionDate());
			dto.setAmount(t.getAmount());
			dto.setEntryType(t.getEntryType());
			dto.setTransferAccountId(t.getTransferAccountId());
			dto.setDescription(t.getDescription());
			dto.setBalanceAfterTxn(t.getBalanceAfterTxn());
			return dto;
		}).toList();
	}

	// fetch transaction between given date
	public List<Transaction> getTransactionBetweenDate(Date fromDate, Date tillDate) {
		return transactionRepository.getTransactionBetweenDate(fromDate, tillDate);
	}

	// fetch transaction from a given date to till now
	public List<Transaction> getTransactionsFromDate(Date fromDate) {
		return transactionRepository.getTransactionFromDate(fromDate);
	}

	// fetch all
	public List<Transaction> getAll() {
		return transactionRepository.findAll();
	}

	// fetch transfer type from enum
	public List<String> getTransferType() {
		List<String> transferType = Arrays.stream(TransferType.values()).map(t -> t.name()).toList();
		return transferType;
	}
}
