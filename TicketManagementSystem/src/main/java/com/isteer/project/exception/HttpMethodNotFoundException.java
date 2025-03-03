package com.isteer.project.exception;

import com.isteer.project.enums.ResponseMessageEnum;

public class HttpMethodNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final ResponseMessageEnum error;
	
	public HttpMethodNotFoundException(ResponseMessageEnum httpMethodNotFoundException) {
		super(httpMethodNotFoundException.getResponseMessage());
		this.error = httpMethodNotFoundException;
	}

	public ResponseMessageEnum getError() {
		return error;
	}
}
