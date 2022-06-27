package com.bw.exceptions;

public class ControlNotReadyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String controlName;

	public ControlNotReadyException(String controlName) {
		this.controlName = controlName;
	}

	public String getMessage() {
		return "The control " + this.controlName + " is not ready.";
	}
}
