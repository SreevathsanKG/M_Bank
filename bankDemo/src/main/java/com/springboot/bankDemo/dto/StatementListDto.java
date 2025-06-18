package com.springboot.bankDemo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.springboot.bankDemo.enums.EntryType;

@Component
public class StatementListDto {

	private String transactionType;
	private LocalDate transactionDate;
	private BigDecimal amount;
	private EntryType entryType;
	private String description;
	private BigDecimal balanceAfterTxn;
	
	public StatementListDto() {	}

	public StatementListDto(String transactionType, LocalDate transactionDate, BigDecimal amount,
			EntryType entryType, String description, BigDecimal balanceAfterTxn) {
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.entryType = entryType;
		this.description = description;
		this.balanceAfterTxn = balanceAfterTxn;
	}

	public String getTransactionType() { return transactionType; }
	public void setTransactionType(String transactionType) { this.transactionType = transactionType; } 
	public LocalDate getTransactionDate() { return transactionDate; }
	public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
	public BigDecimal getAmount() { return amount; }
	public void setAmount(BigDecimal amount) { this.amount = amount; }
	public EntryType getEntryType() { return entryType; }
	public void setEntryType(EntryType entryType) { this.entryType = entryType; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public BigDecimal getBalanceAfterTxn() { return balanceAfterTxn; 	}
	public void setBalanceAfterTxn(BigDecimal balanceAfterTxn) { this.balanceAfterTxn = balanceAfterTxn; }
}
