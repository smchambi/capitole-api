package com.capitole.core.usecase.product;

import com.capitole.core.exception.ValidationException;
import java.util.Optional;

public class MandatoryFieldHelper {
	private static final String MANDATORY_FIELD_ERROR_CODE = "mandatory.field";
	private static final String MANDATORY_FIELD_ERROR_MESSAGE = "Mandatory field is null or empty";

	private MandatoryFieldHelper() {

	}

	public static <T> T getValue(T value) {
		return Optional.ofNullable(value)
				.orElseThrow(() -> new ValidationException(MANDATORY_FIELD_ERROR_CODE, MANDATORY_FIELD_ERROR_MESSAGE));
	}
}
