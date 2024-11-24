package com.tus.exception;

/**
 * General exception for a cart not found for a customer.
 */
public class OrderNotFoundException extends OrderException {

	private static final long serialVersionUID = -7844164306968873458L;

	public OrderNotFoundException(final long customerAccountId) {
		super("No order found: "+ customerAccountId);
	}

}
