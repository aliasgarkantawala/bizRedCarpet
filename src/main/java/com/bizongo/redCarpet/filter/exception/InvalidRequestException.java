package com.bizongo.redCarpet.filter.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InvalidRequestException(Exception e) {
		super(e.getMessage());
		this.setStackTrace(e.getStackTrace());
		this.setMessage(e.getMessage());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
