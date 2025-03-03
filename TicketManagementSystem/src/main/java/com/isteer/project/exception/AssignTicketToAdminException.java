package com.isteer.project.exception;

import com.isteer.project.enums.ResponseMessageEnum;

public class AssignTicketToAdminException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ResponseMessageEnum error;
	
	public AssignTicketToAdminException(ResponseMessageEnum assignTicketToAdminException) {
		super(assignTicketToAdminException.getResponseMessage());
		this.error = assignTicketToAdminException;
	}

	public ResponseMessageEnum getError() {
		return error;
	}
}
