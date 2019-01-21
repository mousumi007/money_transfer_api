package com.app.money.transfer.exception;

public class UncaughtException extends RuntimeException {

	static String LOGPREFIX = "UncaughtException |";
	
	private static final long serialVersionUID = 6441691344451313261L;
	
	public UncaughtException(String message) {
		super(message);
	}

}
