package com.tus.services;

public interface InventoryService {
	 boolean isAvailable(Long productId, int quantity);
	 void deductItem(Long productId, int quantity);
}
