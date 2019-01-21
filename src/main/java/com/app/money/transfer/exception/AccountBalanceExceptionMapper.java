package com.app.money.transfer.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.money.transfer.model.ErrorMessage;

@Provider
public class AccountBalanceExceptionMapper implements ExceptionMapper<AccountBalanceException>{

	static String LOGPREFIX = "AccountBalanceExceptionMapper |";

	@Override
	public Response toResponse(AccountBalanceException exception) {
		
		ErrorMessage error = new ErrorMessage(exception.getMessage());

		return Response.status(Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();
	}
	
}
