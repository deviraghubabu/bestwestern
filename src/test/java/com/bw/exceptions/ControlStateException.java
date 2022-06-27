package com.bw.exceptions;

public class ControlStateException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ControlStateException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
