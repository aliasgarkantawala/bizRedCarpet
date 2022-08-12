package com.bizongo.redCarpet.filter.exception;

public class InvalidJWTException extends UnAuthorisedUserException {

	private static final long serialVersionUID = 1L;

	public InvalidJWTException(String _message) {
		super(_message);
	}

}
