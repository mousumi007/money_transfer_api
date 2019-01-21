package com.app.money.transfer.exception;

public class TransactionDeniedException extends RuntimeException{
	
	private static final long serialVersionUID = 8720398554676091668L;
	
	static String LOGPREFIX = "TransactionDeniedException |";

	public TransactionDeniedException(String message) {
		super(message);
	}
}
