package com.tus.orders;

public class Customer {
	private Order order;
	private long accountNumber;
	private String customerName;
	private String customerEmail;
	private String customerAddress;

	public Order getOrder() {
		return order;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(final long number) {
		this.accountNumber = number;
	}

	public String getName() {
		return customerName;
	}

	public void setName(final String name) {
		this.customerName = name;
	}

	public String getEmail() {
		return customerEmail;
	}

	public void setEmail(final String email) {
		this.customerEmail = email;
	}

	public String getAddress() {
		return customerAddress;
	}

	public void setAddress(final String address) {
		this.customerAddress = address;
	}

	public void setOrder(final Order order) {
		this.order = order;
	}


	

}
