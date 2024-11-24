package com.tus.exception;

public class InventoryException extends CartException {

	private static final long serialVersionUID = 334051992916748022L;

	public InventoryException(final String message) {
		super(message);
	}

}
