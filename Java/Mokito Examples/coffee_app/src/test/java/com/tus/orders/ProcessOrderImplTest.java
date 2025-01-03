package com.tus.orders;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.internal.verification.Times;

import com.tus.coffee.ProcessOrderImpl;
import com.tus.dao.OrderDAO;
import com.tus.exception.CustomerNotFoundException;
import com.tus.exception.OrderDAOException;
import com.tus.exception.OrderEmptyException;
import com.tus.exception.OrderException;
import com.tus.exception.OrderNotFoundException;
import com.tus.exception.OrderPaymentException;
import com.tus.services.ApplePayHandler;
import com.tus.services.Barista;
import com.tus.services.Invoicer;


public class ProcessOrderImplTest {

	private Customer customer;
	private Order order;

	private ProcessOrderImpl processOrderImpl;
	private final Invoicer invoicer = mock(Invoicer.class);
	private final Barista barista = mock(Barista.class);
	private final OrderDAO orderDAO = mock(OrderDAO.class);
	private final ApplePayHandler applePayHandler = mock(ApplePayHandler.class);
	final static long ACCOUNT_NUMBER = 123456L;
	static final String CUSTOMER_NAME = "Joe";
	static final String CUSTOMER_EMAIL = "joe@gmail.com";

	@BeforeEach
	public void setUp() {
		customer = new Customer();
		order = new Order();
		customer.setAccountNumber(ACCOUNT_NUMBER);
		customer.setEmail("joe@gmail.com");
		customer.setAddress("Athlone");
		customer.setName("Joe");

		processOrderImpl = new ProcessOrderImpl(barista, invoicer, orderDAO, applePayHandler);

	}

	// Test 1
	@Test
	void testCustomerNotFoundException() throws OrderException {
		Throwable exception = assertThrows(CustomerNotFoundException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Unknown Customer: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(barista, new Times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
		verify(applePayHandler, new Times(0)).pay(anyString(), anyString(), anyDouble());

	}

	// Test 2
	@Test
	public void testOrderDAOSQLException() throws OrderException, SQLException {
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenThrow(SQLException.class);
		Throwable exception = assertThrows(OrderDAOException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Error connecting to database: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(barista, new Times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
		verify(applePayHandler, new Times(0)).pay(anyString(), anyString(), anyDouble());
	}

	// Test 3
	@Test
	public void testOrderNotFoundException() throws OrderException, SQLException {
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		Throwable exception = assertThrows(OrderNotFoundException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("No order found: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(barista, new Times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
		verify(applePayHandler, new Times(0)).pay(anyString(), anyString(), anyDouble());
	}

	// Test 4
	@Test
	public void testOrderEmptyException() throws OrderException, SQLException {
		customer.setOrder(order);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		Throwable exception = assertThrows(OrderEmptyException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Cart is empty: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(barista, new Times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
		verify(applePayHandler, new Times(0)).pay(anyString(), anyString(), anyDouble());
	}

	// Test5
	@Test
	public void testOneItemInOrder() throws OrderException, SQLException {
		final Product productOne = new Product("Tea", 2, "PC001");
		customer.setOrder(order);
		order.addItem(productOne, 1);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, "joe@gmail.com", 2.0)).thenReturn("INV123");
		processOrderImpl.processOrder(ACCOUNT_NUMBER);
		verify(barista).prepareItem("PC001", 1, CUSTOMER_NAME);
		verify(invoicer).invoiceCustomer(ACCOUNT_NUMBER, CUSTOMER_EMAIL, 2.0);
		verify(applePayHandler).pay("INV123", CUSTOMER_EMAIL, 2.0);
	}

	// Test6
	@Test
	public void testTwoItemsInOrder() throws OrderException, SQLException {
		final Product productOne = new Product("Tea", 2, "PC001");
		final Product productTwo = new Product("Coffee", 3, "PC002");
		customer.setOrder(order);
		order.addItem(productOne, 1);
		order.addItem(productTwo, 1);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, "joe@gmail.com", 5.0)).thenReturn("INV123");
		processOrderImpl.processOrder(ACCOUNT_NUMBER);
		verify(barista, new Times(1)).prepareItem("PC001", 1, CUSTOMER_NAME);
		verify(barista, new Times(1)).prepareItem("PC002", 1, CUSTOMER_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, CUSTOMER_EMAIL, 5.0);
		verify(applePayHandler, new Times(1)).pay("INV123", CUSTOMER_EMAIL, 5.0);

	}

	// Test 7
	@Test
	public void testOneTeaTwoCoffeeItemsInOrder() throws OrderException, SQLException {
		final Product productOne = new Product("Tea", 2, "PC001");
		final Product productTwo = new Product("Coffee", 3, "PC002");
		customer.setOrder(order);
		order.addItem(productOne, 1);
		order.addItem(productTwo, 2);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, "joe@gmail.com", 8.0)).thenReturn("INV123");
		processOrderImpl.processOrder(ACCOUNT_NUMBER);
		verify(barista, new Times(1)).prepareItem("PC001", 1, CUSTOMER_NAME);
		verify(barista, new Times(1)).prepareItem("PC002", 2, CUSTOMER_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, CUSTOMER_EMAIL, 8.0);
		verify(applePayHandler, new Times(1)).pay("INV123", CUSTOMER_EMAIL, 8.0);
	}

	// Test8
	@Test
	public void testOrderPaymentException() throws OrderException, SQLException {
		final Product productOne = new Product("Tea", 2, "PC001");
		order.addItem(productOne, 1);
		customer.setOrder(order);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, "joe@gmail.com", 2.0)).thenReturn("INV123");
		doThrow(OrderPaymentException.class).when(applePayHandler).pay("INV123", CUSTOMER_EMAIL, 2.0);
		Throwable exception = assertThrows(OrderPaymentException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Problem with payment: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(barista, new Times(0)).prepareItem("PC001", 1, CUSTOMER_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, CUSTOMER_EMAIL, 2.0);
		verify(applePayHandler, new Times(1)).pay("INV123", CUSTOMER_EMAIL, 2.0);
	}

}
