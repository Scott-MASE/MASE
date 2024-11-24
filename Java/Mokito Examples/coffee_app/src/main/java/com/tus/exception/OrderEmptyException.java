package com.tus.exception;

public class OrderEmptyException extends OrderException {

	private static final long serialVersionUID = 334051992916748022L;

	public OrderEmptyException(final long customerAccountId) {
		super("Cart is empty: "+ customerAccountId);
	}

}
