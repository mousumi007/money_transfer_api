package com.app.money.transfer.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.money.transfer.model.ErrorMessage;

@Provider
public class TransactionDeniedExceptionMapper implements ExceptionMapper<TransactionDeniedException>{

	static String LOGPREFIX = "AccountBalanceExceptionMapper |";

	@Override
	public Response toResponse(TransactionDeniedException exception) {
		
		ErrorMessage error = new ErrorMessage(exception.getMessage());

		return Response.status(Status.FORBIDDEN).entity(error).type(MediaType.APPLICATION_JSON).build();
	}
	
}
