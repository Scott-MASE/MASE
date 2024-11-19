package com.tus.checkout;

import com.tus.cart.Customer;
import com.tus.exception.CartException;

public interface CheckoutService {
	
	 void checkout(Customer customer) throws CartException;
}
