package com.app.money.transfer.model;

public class BankDetails {

	private String accountId;
	private String bankCode;
	
	
	public BankDetails() {
		
	}

	public BankDetails(String accountId, String bankCode) {
		this.accountId = accountId;
		this.bankCode = bankCode;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
