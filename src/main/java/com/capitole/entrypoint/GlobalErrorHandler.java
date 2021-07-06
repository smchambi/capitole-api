package com.capitole.entrypoint;

import com.capitole.core.exception.BaseException;
import com.capitole.core.exception.EntityNotFoundException;
import com.capitole.core.exception.UseCaseException;
import com.capitole.core.exception.ValidationException;
import com.capitole.entrypoint.response.ErrorResult;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {
	private static final String INTERNAL_SERVER_ERROR_CODE = "internal_error";
	private static final String EXCEPTION_LOG_MESSAGE = "[status_code:{}][error_code:{}][exception:{}] Message is: {}";

	private static final Logger LOG = LoggerFactory.getLogger(GlobalErrorHandler.class);

	/**
	 * Map Exception type to specific error.
	 */
	private static final Map<Class<? extends BaseException>, HttpStatus> EXCEPTION_STATUS_CODE =
			Map.ofEntries(
					Map.entry(ValidationException.class, HttpStatus.BAD_REQUEST),
					Map.entry(EntityNotFoundException.class, HttpStatus.NOT_FOUND)
			);

	/**
	 * Handle any error not expected.
	 *
	 * @param error   unexpected error into the application
	 * @param request that causes this error
	 * @return Error response
	 * @see ErrorResult
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(final Exception error, final HttpServletRequest request) {
		LOG.error(EXCEPTION_LOG_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_CODE, error.getClass().getSimpleName(), error.getMessage(), error);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				ErrorResult.builder()
						.timestamp(LocalDateTime.now())
						.error(INTERNAL_SERVER_ERROR_CODE)
						.message(error.getMessage())
						.path(request.getRequestURI())
						.build());
	}

	/**
	 * Handle exception manually generated into application.
	 *
	 * @param error   that throw manually into the application
	 * @param request that causes this error
	 * @return Error response
	 * @see ErrorResult
	 */
	@ExceptionHandler(UseCaseException.class)
	public ResponseEntity<Object> handle(final UseCaseException error, final HttpServletRequest request) {
		var originalCause = Optional.ofNullable(error.getCause())
				.filter(BaseException.class::isInstance)
				.map(cause -> (BaseException) cause).orElse(error);

		var status = EXCEPTION_STATUS_CODE.getOrDefault(originalCause.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
		LOG.error(EXCEPTION_LOG_MESSAGE, status.value(), originalCause.getErrorCode(), originalCause.getClass().getSimpleName(), error.getMessage(), error);
		return ResponseEntity.status(status).body(
				ErrorResult.builder()
						.timestamp(LocalDateTime.now())
						.error(originalCause.getErrorCode())
						.message(originalCause.getMessage())
						.path(request.getRequestURI())
						.build());
	}
}
