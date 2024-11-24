package com.tus.coffee;

import com.tus.exception.OrderException;

public interface ProcessOrder {

	
	
	 void processOrder(String accountId) throws OrderException;
}
