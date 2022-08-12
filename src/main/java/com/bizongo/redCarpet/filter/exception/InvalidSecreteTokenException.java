package com.bizongo.redCarpet.filter.exception;

public class InvalidSecreteTokenException  extends UnAuthorisedUserException {

	private static final long serialVersionUID = 1L;

	public InvalidSecreteTokenException(String _message) {
		super(_message);
	}

}