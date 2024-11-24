package com.tus.payment;

import com.tus.exception.PaymentException;

public interface PaymentGateway {
	void processPayPal(double amount, String emailId, String password) throws PaymentException;
	void processCreditCard(double amount, String cardNumber, String cvv, String DateOfExpiry) throws PaymentException;
}
