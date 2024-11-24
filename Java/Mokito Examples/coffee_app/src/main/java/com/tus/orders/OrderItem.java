package com.tus.orders;

public class OrderItem {
	private final Product product;
	private final int quantity;
	
	public OrderItem(final Product product, final int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQty() {
		return quantity;
	}
	
}
