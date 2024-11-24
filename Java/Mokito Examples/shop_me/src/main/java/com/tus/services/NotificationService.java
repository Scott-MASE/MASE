package com.tus.services;

import com.tus.cart.Cart;

public interface NotificationService {
	void notifyOrderProcessed(Cart cart);
}
