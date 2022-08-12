package com.bizongo.redCarpet.filter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnAuthorisedUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String message;
	
	public UnAuthorisedUserException(String userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
	}

	public UnAuthorisedUserException(String _message) {
		super(_message);
		this.setMessage(_message);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
