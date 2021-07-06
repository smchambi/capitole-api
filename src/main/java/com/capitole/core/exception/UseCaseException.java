package com.capitole.core.exception;

public class UseCaseException extends BaseException {
	public UseCaseException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
}
