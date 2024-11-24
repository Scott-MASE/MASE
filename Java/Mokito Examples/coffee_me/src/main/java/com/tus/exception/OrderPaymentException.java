package com.tus.exception;

/**
 * General exception for a cart not found for a customer.
 */
public class OrderPaymentException extends OrderException {

	private static final long serialVersionUID = -7844164306968873458L;

	public OrderPaymentException(final long customerAccountId) {
		super("Problem with payment: "+ customerAccountId);
	}

}
