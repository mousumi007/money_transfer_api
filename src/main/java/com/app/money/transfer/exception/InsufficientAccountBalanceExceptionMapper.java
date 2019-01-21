package com.app.money.transfer.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.money.transfer.model.ErrorMessage;

@Provider
public class InsufficientAccountBalanceExceptionMapper implements ExceptionMapper<InsufficientAccountBalanceException> {

	@Override
	public Response toResponse(InsufficientAccountBalanceException ex) {

		ErrorMessage error = new ErrorMessage(ex.getMessage());

		return Response.status(Status.EXPECTATION_FAILED).entity(error).type(MediaType.APPLICATION_JSON).build();
	}
}
