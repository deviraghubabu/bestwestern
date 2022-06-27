package com.bw.exceptions;

public class UnableToRetreiveValueException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UnableToRetreiveValueException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
