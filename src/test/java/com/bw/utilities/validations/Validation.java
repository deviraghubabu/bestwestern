package com.bw.utilities.validations;

public class Validation {
	private ValidationStep step;
	private Object target;
	private String name;
	private String failureMessage;
	private long timeout = 0L;

	private Validation(ValidationStep step, Object target, String validationName, String failureMessage) {
		this.step = step;
		this.target = target;
		this.failureMessage = failureMessage;
		this.name = validationName;
	}

	public static Validation of(ValidationStep step, Object target, String validationName, String failureMessage) {
		return new Validation(step, target, validationName, failureMessage);
	}

	public static Validation of(ValidationStep step, String validationName, String failureMessage) {
		return new Validation(step, (Object) null, validationName, failureMessage);
	}

	public Validation withTimeout(long timeoutInMillis) {
		this.timeout = timeoutInMillis;
		return this;
	}

	public ValidationStep getStep() {
		return this.step;
	}

	public Object getTarget() {
		return this.target;
	}

	public String getName() {
		return this.name;
	}

	public String getMessage() {
		return this.failureMessage;
	}

	public long getTimeout() {
		return this.timeout;
	}

	public String toString() {
		return this.getName() + (this.target != null ? ", targeting: " + this.getTarget() : "");
	}
}
