package com.tus.exception;

public class CustomerNotFoundException extends OrderException {

	private static final long serialVersionUID = 334051992916748022L;

	public CustomerNotFoundException(final long customerAccountId) {
		super("Unknown Customer: "+ customerAccountId);
	}

}
