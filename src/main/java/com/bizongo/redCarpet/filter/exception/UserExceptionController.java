package com.bizongo.redCarpet.filter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bizongo.redCarpet.constant.BizRedCarpetConstants;

@ControllerAdvice
public class UserExceptionController {
	
	@ExceptionHandler(value = UnAuthorisedUserException.class)
	public ResponseEntity<UserExceptionDetails> exception(UnAuthorisedUserException exception) {
		return new ResponseEntity<UserExceptionDetails>(
				new UserExceptionDetails(exception.getUserId(), 
						String.format(BizRedCarpetConstants.BRC_UNAOTHORIZED_USER_MESSAGE,
								exception.getUserId())), 
				HttpStatus.UNAUTHORIZED);
	}
}
