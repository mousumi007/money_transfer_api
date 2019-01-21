package com.app.money.transfer.exception;

public class AccountBalanceException extends RuntimeException{
	
	private static final long serialVersionUID = 8720398554676091668L;
	
	static String LOGPREFIX = "AccountBalanceException |";

	public AccountBalanceException(String message) {
		super(message);
	}
}
