package com.tus.coffee;

import java.sql.SQLException;
import java.util.ArrayList;

import com.tus.orders.OrderItem;
import com.tus.orders.Customer;
import com.tus.orders.Order;
import com.tus.orders.Product;
import com.tus.dao.OrderDAO;
import com.tus.exception.OrderDAOException;
import com.tus.exception.OrderEmptyException;
import com.tus.exception.OrderException;
import com.tus.exception.OrderNotFoundException;
import com.tus.exception.OrderPaymentException;
import com.tus.exception.CustomerNotFoundException;
import com.tus.services.ApplePayHandler;
import com.tus.services.Invoicer;
import com.tus.services.Barista;

public class ProcessOrderImpl {

	private final Barista barista;
	private final Invoicer invoicer;
	private final OrderDAO orderDAO;
	private Customer customer;
	private long customerAccountId;
	private Order order;
	private final ApplePayHandler applePayHandler;

	public ProcessOrderImpl(final Barista barista,
			final Invoicer invoicer, final OrderDAO orderDAO,
			final ApplePayHandler applePayHandler) {
		this.orderDAO = orderDAO;
		this.barista = barista;
		this.invoicer = invoicer;
		this.applePayHandler = applePayHandler;
	}

	public void processOrder(final long customerAccountId)
			throws OrderException, SQLException {
		try {
			this.customerAccountId = customerAccountId;
			customer = orderDAO.findCustomerForId(customerAccountId);
			if (customer != null) {
				completeOrder(customer);
			} else {
				throw new CustomerNotFoundException(customerAccountId);

			}
		} catch (SQLException e) {
			throw new OrderDAOException(customerAccountId);
		}
	}

	private void completeOrder(final Customer customer) throws OrderException {
		order = customer.getOrder();
		if (order == null) {
			throw new OrderNotFoundException(customerAccountId);
		} else {
			final ArrayList<OrderItem> orderItems = (ArrayList<OrderItem>) order
					.getOrderItems();
			if (orderItems.isEmpty()) {
				throw new OrderEmptyException(customerAccountId);
			}
			final int orderTotal = order.getTotalPrice();
			final String invoiceNumber = sendInvoice(orderTotal);
			processPayment(invoiceNumber, customer.getEmail(), orderTotal);
			sendToBarista(orderItems);

		}

	}

	private String sendInvoice(final int orderTotal) {
		return invoicer.invoiceCustomer(customer.getAccountNumber(),
				customer.getEmail(), orderTotal);
	}

	private void sendToBarista(final ArrayList<OrderItem> orderItems) {
		Product product;
		for (final OrderItem orderItem : orderItems) {
			product = orderItem.getProduct();
			barista.prepareItem(product.getProductCode(),
					orderItem.getQty(), customer.getName());
		}
	}

	private void processPayment(final String invoiceNumber, final String customerEmailAddress, final int orderTotal) throws OrderException {
		try {
			applePayHandler.pay(invoiceNumber, customerEmailAddress, orderTotal);
		} catch (OrderException e) {
			throw new OrderPaymentException(customerAccountId);
		}
			
		
	}

}
