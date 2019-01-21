package com.app.money.transfer.exception;

public class InsufficientAccountBalanceException extends RuntimeException {

	static String LOGPREFIX = "InsufficientAccountBalanceException |";
	
	private static final long serialVersionUID = -3309307869465959312L;
	
	public InsufficientAccountBalanceException(String message) {
		super(message);
	}
}
