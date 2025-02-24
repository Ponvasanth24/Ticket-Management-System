package com.isteer.project.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isteer.project.dto.ErrorResponseDto;
import com.isteer.project.enums.ResponseMessageEnum;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponseDto> accessDeniedException(
			AccessDeniedException err) {
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.ACCESS_DENIED_EXCEPTION.getResponseCode(),
				ResponseMessageEnum.ACCESS_DENIED_EXCEPTION.getResponseMessage());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> usernameNotFoundException(UsernameNotFoundException err) {
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.USERNAME_NOT_FOUND_EXCEPTION.getResponseCode(), ResponseMessageEnum.USERNAME_NOT_FOUND_EXCEPTION.getResponseMessage());
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponseDto> globalExceptionHandler(
			RuntimeException err) {
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.GLOBAL_EXCEPTION_HANDLER.getResponseCode(),
				ResponseMessageEnum.GLOBAL_EXCEPTION_HANDLER.getResponseMessage());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
