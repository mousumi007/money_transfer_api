package com.app.money.transfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalance {

	@JsonProperty("accountNumber")
	private String accountId;
	
	@JsonProperty("balance")
	private String accountbalance;

	public AccountBalance() {
		
	}

	public AccountBalance(String accountId, String accountbalance) {
		super();
		this.accountId = accountId;
		this.accountbalance = accountbalance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountbalance() {
		return accountbalance;
	}

	public void setAccountbalance(String accountbalance) {
		this.accountbalance = accountbalance;
	}
	
}
