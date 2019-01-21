package com.app.money.transfer.exception;

public class InvalidUserIdException extends RuntimeException{

	private static final long serialVersionUID = 9020781998011027694L;
	
	static String LOGPREFIX = "InvalidUserIdException |";
	
	public InvalidUserIdException(String message) {
		super(message);
	}
}



