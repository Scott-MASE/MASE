package com.tus.services;

import com.tus.exception.OrderPaymentException;

public interface ApplePayHandler{
	void pay(String invoiceId,String emailAddress, double amount) throws OrderPaymentException;
}
