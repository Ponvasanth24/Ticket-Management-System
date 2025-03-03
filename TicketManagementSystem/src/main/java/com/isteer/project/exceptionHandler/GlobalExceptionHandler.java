package com.isteer.project.exceptionHandler;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isteer.project.dto.ErrorResponseDto;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.AssignTicketToAdminException;
import com.isteer.project.exception.HttpMethodNotFoundException;
import com.isteer.project.exception.RoleNotFoundException;
import com.isteer.project.exception.TicketIdNotFoundException;
import com.isteer.project.exception.UrlNotFoundException;
import com.isteer.project.exception.UserNameNotFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TicketIdNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> ticketIdNotFoundException(TicketIdNotFoundException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNameNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> userNameNotFoundException(UserNameNotFoundException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AssignTicketToAdminException.class)
	public ResponseEntity<ErrorResponseDto> assignTicketToAdminException(AssignTicketToAdminException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> roleNotFoundException(RoleNotFoundException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UrlNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> urlNotFoundException(UrlNotFoundException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMethodNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> httpMethodNotFoundException(HttpMethodNotFoundException err) {
		ResponseMessageEnum errorCode = err.getError();
		ErrorResponseDto errorMessage = new ErrorResponseDto(errorCode.getResponseCode(), errorCode.getResponseMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
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
	
	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<ErrorResponseDto> duplicateKeyException(DuplicateKeyException err) {
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.DUPLICATE_KEY_EXCEPTION.getResponseCode(), ResponseMessageEnum.DUPLICATE_KEY_EXCEPTION.getResponseMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponseDto> globalExceptionHandler(
			RuntimeException err) {
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.GLOBAL_EXCEPTION_HANDLER.getResponseCode(),
				ResponseMessageEnum.GLOBAL_EXCEPTION_HANDLER.getResponseMessage());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
