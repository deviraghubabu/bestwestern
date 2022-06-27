package com.bw.exceptions;

public class UnexpectedValueException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UnexpectedValueException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
