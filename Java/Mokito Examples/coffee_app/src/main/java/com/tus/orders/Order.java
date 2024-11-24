package com.tus.orders;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private final List<OrderItem> orderItems = new ArrayList<OrderItem>();


	public void addItem(final Product product, final int quantity) {
		final OrderItem cartItem = new OrderItem(product, quantity);
		orderItems.add(cartItem);
	}


	public int getTotalPrice() {
		int total = 0;
		for (final OrderItem item : orderItems) {
			total += item.getProduct().getPrice() * item.getQty();
		}
		return total;
	}
	
	public List<OrderItem> getOrderItems(){
		return orderItems;
	}

}
