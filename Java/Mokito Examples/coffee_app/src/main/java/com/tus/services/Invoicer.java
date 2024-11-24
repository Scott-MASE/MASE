package com.tus.services;

public interface Invoicer {

	String invoiceCustomer(long customerNumber, String customerEmail,
			double amount);

}
