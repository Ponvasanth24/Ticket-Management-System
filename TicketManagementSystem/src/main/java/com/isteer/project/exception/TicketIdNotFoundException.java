package com.isteer.project.exception;

import com.isteer.project.enums.ResponseMessageEnum;

public class TicketIdNotFoundException extends RuntimeException{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ResponseMessageEnum error;
	
	public TicketIdNotFoundException(ResponseMessageEnum ticketIdNotFoundException) {
		super(ticketIdNotFoundException.getResponseMessage());
		this.error = ticketIdNotFoundException;
	}

	public ResponseMessageEnum getError() {
		return error;
	}
}
