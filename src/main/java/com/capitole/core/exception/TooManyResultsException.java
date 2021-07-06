package com.capitole.core.exception;

public class TooManyResultsException extends RepositoryException {
	public TooManyResultsException(String errorCode, String message) {
		super(errorCode, message);
	}
}
