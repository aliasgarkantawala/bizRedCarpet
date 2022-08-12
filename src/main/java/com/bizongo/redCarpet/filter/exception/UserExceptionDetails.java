package com.bizongo.redCarpet.filter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserExceptionDetails {
	
	private String userId;
	private String message;
	
	
	public UserExceptionDetails(String userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
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
