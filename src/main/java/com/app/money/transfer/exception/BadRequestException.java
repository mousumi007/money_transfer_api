package com.app.money.transfer.exception;

import java.util.ArrayList;

public class BadRequestException {

	private ArrayList<String> errorMessage;
	
	public BadRequestException() {
		
	}
	
	public BadRequestException(ArrayList<String> message) {
		super();
		this.errorMessage = message;
		
	}

	public ArrayList<String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ArrayList<String> errorMessage) {
		this.errorMessage = errorMessage;
	}

}
