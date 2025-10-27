package com.custodia.supply.validation.util;

public final class Exceptions {
	private Exceptions() {
	}

	public static BadRequestException badRequest(String message) {
		return new BadRequestException(message);
	}

	public static NotFoundException notFound(Class<?> type, Object id) {
		return new NotFoundException(type.getSimpleName(), "id=" + id);
	}

	public static NotFoundException notFound(Class<?> type, String key, Object value) {
		return new NotFoundException(type.getSimpleName(), key + "=" + value);
	}
}
