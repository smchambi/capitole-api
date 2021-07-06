package com.capitole.entrypoint.response;

import java.time.LocalDateTime;
import java.util.Objects;

public class ErrorResult {
	private final String message;
	private final String error;
	private final LocalDateTime timestamp;
	private final String path;

	private ErrorResult(String message, String error, LocalDateTime timestamp, String path) {
		this.message = message;
		this.error = error;
		this.timestamp = timestamp;
		this.path = path;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		ErrorResult that = (ErrorResult) object;
		return message.equals(that.message) && error.equals(that.error) && path.equals(that.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message, error, path);
	}

	public static class ErrorResultBuilder {
		private String message;
		private String error;
		private LocalDateTime timestamp;
		private String path;

		public ErrorResultBuilder message(String message) {
			this.message = message;
			return this;
		}

		public ErrorResultBuilder error(String error) {
			this.error = error;
			return this;
		}

		public ErrorResultBuilder timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public ErrorResultBuilder path(String path) {
			this.path = path;
			return this;
		}

		public ErrorResult build() {
			return new ErrorResult(message, error, timestamp, path);
		}
	}

	public static ErrorResultBuilder builder() {
		return new ErrorResultBuilder();
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getPath() {
		return path;
	}
}
