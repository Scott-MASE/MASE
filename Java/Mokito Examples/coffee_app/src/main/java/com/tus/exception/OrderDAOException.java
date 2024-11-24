package com.tus.exception;

public class OrderDAOException extends OrderException {

	private static final long serialVersionUID = 334051992916748022L;

	public OrderDAOException(final long customerAccountId) {
		super("Error connecting to database: "+ customerAccountId);
	}

}
