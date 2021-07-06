package com.capitole.core.exception;

public class ValidationException extends BaseException {
	public ValidationException(String code, String message) {
		super(code, message);
	}
}
