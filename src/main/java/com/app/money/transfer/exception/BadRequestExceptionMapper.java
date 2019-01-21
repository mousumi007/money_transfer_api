package com.app.money.transfer.exception;

import java.util.ArrayList;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.money.transfer.model.ErrorMessage;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {

		BadRequestException error = new BadRequestException(prepareMessage(exception));

		return Response.status(Status.BAD_REQUEST).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

	private ArrayList<String> prepareMessage(ConstraintViolationException exception) {
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		
		for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			errorMessages.add(cv.getMessage());
			
		}
		return errorMessages;
	}
}
