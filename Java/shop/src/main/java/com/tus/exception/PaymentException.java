package com.tus.exception;

public class PaymentException extends CartException {

	private static final long serialVersionUID = 334051992916748022L;

	public PaymentException(final String message) {
		super(message);
	}

}
