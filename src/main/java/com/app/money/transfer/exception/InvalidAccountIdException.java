package com.app.money.transfer.exception;

public class InvalidAccountIdException extends RuntimeException{

	static String LOGPREFIX = "InvalidAccountIdException |";
	
	private static final long serialVersionUID = -3309307869465959312L;
	
	public InvalidAccountIdException(String message) {
		super(message);
	}

}
