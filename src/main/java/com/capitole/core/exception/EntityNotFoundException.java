package com.capitole.core.exception;

public class EntityNotFoundException extends BaseException {
	private static final String ERROR_CODE = "not.found";

	public EntityNotFoundException(String message) {
		super(ERROR_CODE, message);
	}
}
