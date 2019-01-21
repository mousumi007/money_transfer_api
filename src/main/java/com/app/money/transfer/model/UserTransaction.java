package com.app.money.transfer.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserTransaction {

	@NotNull(message = "userId cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9@_]*$", message = "Invalid userId")
	private String userId;
	
	@NotNull(message = "accountId cannot be null")
	@Pattern(regexp = "^[A-Z0-9]*$", message = "Invalid accountId")
	private String accountId;
	
	@NotNull(message = "beneficiaryAccountId cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9@_]*$", message = "Invalid beneficiaryAccountId")
	private String beneficiaryAccountId;
	
	@NotNull(message = "bankCode cannot be null")
	@Pattern(regexp = "^[A-Z0-9]*$", message = "Invalid bankCode")
	private String bankCode;
	
	@NotNull(message = "amount cannot be null")
	@Pattern(regexp = "^[0-9.]*$", message = "Invalid amount")
	private String amount;
	
	@NotNull(message = "reason cannot be null")
	@Pattern(regexp = "^[A-Za-z0-9\\s]*$", message = "Invalid reason")
	private String reason;
	
	public UserTransaction() {
		
	}
	
	public UserTransaction(String userId, String accountId, String beneficiaryAccountId, String bankCode, String amount, String reason) {
		this.userId = userId;
		this.accountId = accountId;
		this.beneficiaryAccountId = beneficiaryAccountId;
		this.bankCode = bankCode;
		this.amount = amount;
		this.reason = reason;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
