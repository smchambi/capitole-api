package com.capitole.core.exception;

public class RepositoryException extends BaseException {
	public RepositoryException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public RepositoryException(String errorCode, String message) {
		super(errorCode, message);
	}
}
