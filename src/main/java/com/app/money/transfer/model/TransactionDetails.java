package com.app.money.transfer.model;

public class TransactionDetails {

	private String accountId;
	private String beneficiaryAccountId;
	private String transactionId;
	private String transactionAmount;
	private String transactionDate;
	private String transactionReason;
	private String transactionStatus;
	private String balance;
	
	public TransactionDetails() {
		
	}

	public TransactionDetails(String accountId, String beneficiaryAccountId, String transactionId,
			String transactionAmount, String transactionDate, String transactionReason, 
			String transactionStatus, String balance) {
		super();
		this.accountId = accountId;
		this.beneficiaryAccountId = beneficiaryAccountId;
		this.transactionId = transactionId;
		this.transactionAmount = transactionAmount;
		this.transactionDate = transactionDate;
		this.transactionReason = transactionReason;
		this.transactionStatus = transactionStatus;
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBeneficiaryAccountId() {
		return beneficiaryAccountId;
	}

	public void setBeneficiaryAccountId(String beneficiaryAccountId) {
		this.beneficiaryAccountId = beneficiaryAccountId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionReason() {
		return transactionReason;
	}

	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
