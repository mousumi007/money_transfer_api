package com.app.money.transfer.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.money.transfer.model.ErrorMessage;

@Provider
public class InvalidUserIdExceptionMapper implements ExceptionMapper<InvalidUserIdException> {

	@Override
	public Response toResponse(InvalidUserIdException ex) {

		ErrorMessage error = new ErrorMessage(ex.getMessage());

		return Response.status(Status.NOT_FOUND).entity(error).type(MediaType.APPLICATION_JSON).build();

	}

}
