package com.isteer.project.exception;

import com.isteer.project.enums.ResponseMessageEnum;

public class UrlNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ResponseMessageEnum error;
	
	public UrlNotFoundException(ResponseMessageEnum urlNotFoundException) {
		super(urlNotFoundException.getResponseMessage());
		this.error = urlNotFoundException;
	}

	public ResponseMessageEnum getError() {
		return error;
	}
}
