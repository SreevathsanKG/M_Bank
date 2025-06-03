package com.springboot.bankDemo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(optional = false)
	private TransactionType transactionType;
	@Column(name = "transacton_date", nullable = false)
	private LocalDate transactionDate;
	@Column(name = "to_account_id", nullable = false)
	private String toAccountId;
	@Column(nullable = false)
	private BigDecimal amount;
	private String description;
	@ManyToOne(optional = false)
	private Account account;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public TransactionType getTransactionType() { return transactionType; }
	public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
	public LocalDate getTransactionDate() { return transactionDate; }
	public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
	public String getAccountId() { return toAccountId; }
	public void setAccountId(String toAccountId) { this.toAccountId = toAccountId; }
	public BigDecimal getAmount() { return amount; }
	public void setAmount(BigDecimal amount) { this.amount = amount; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public Account getAccount() { return account; }
	public void setAccount(Account account) { this.account = account; }
	
}
