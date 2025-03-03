package com.isteer.project.exception;

import com.isteer.project.enums.ResponseMessageEnum;

public class UserNameNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private final ResponseMessageEnum error;
	
	public UserNameNotFoundException(ResponseMessageEnum ticketIdNotFoundException) {
		super(ticketIdNotFoundException.getResponseMessage());
		this.error = ticketIdNotFoundException;
	}

	public ResponseMessageEnum getError() {
		return error;
	}
}
