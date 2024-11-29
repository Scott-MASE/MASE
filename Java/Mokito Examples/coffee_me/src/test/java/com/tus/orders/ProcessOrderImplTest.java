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
import static org.mockito.Mockito.times;
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
	private ProcessOrderImpl processOrderImpl;
	private Customer customer;
	private Order order;

	private final long ACCOUNT_NUMBER = 123L;
	private final String ACCOUNT_ADDRESS = "athlone";
	private final String ACCOUNT_EMAIL = "joe@mail.com";
	private final String ACCOUNT_NAME = "joe";
	private final Product product1 = new Product("hot", 1, "abc");
	private final Product product2 = new Product("cold", 2, "def");

	private final Invoicer invoicer = mock(Invoicer.class);
	private final ApplePayHandler applePayHandler = mock(ApplePayHandler.class);
	private final Barista barista = mock(Barista.class);
	private final OrderDAO orderDAO = mock(OrderDAO.class);

	@BeforeEach
	public void setUp() {
		processOrderImpl = new ProcessOrderImpl(barista, invoicer, orderDAO, applePayHandler);
		customer = new Customer();
		order = new Order();
		customer.setAccountNumber(ACCOUNT_NUMBER);
		customer.setAddress(ACCOUNT_ADDRESS);
		customer.setEmail(ACCOUNT_EMAIL);
		customer.setName(ACCOUNT_NAME);

	}

	// Test 1
	@Test
	public void testCustomerNotFoundException() throws CustomerNotFoundException, OrderPaymentException {
		Throwable exception = assertThrows(CustomerNotFoundException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Unknown Customer: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(applePayHandler, times(0)).pay(anyString(), anyString(), anyDouble());
		verify(barista, times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
	}

	// Test 2
	@Test
	public void testOrderDAOSQLException() throws OrderException, SQLException {
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenThrow(new SQLException());
		Throwable exception = assertThrows(OrderDAOException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Error connecting to database: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(applePayHandler, times(0)).pay(anyString(), anyString(), anyDouble());
		verify(barista, times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
	}

	// Test 3
	@Test
	public void testOrderNotFoundException() throws OrderException, SQLException {
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		Throwable exception = assertThrows(OrderNotFoundException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("No order found: " + ACCOUNT_NUMBER, exception.getMessage());
		verify(applePayHandler, times(0)).pay(anyString(), anyString(), anyDouble());
		verify(barista, times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
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
		verify(applePayHandler, times(0)).pay(anyString(), anyString(), anyDouble());
		verify(barista, times(0)).prepareItem(anyString(), anyInt(), anyString());
		verify(invoicer, new Times(0)).invoiceCustomer(anyLong(), anyString(), anyDouble());
	}

	// Test5
	@Test
	public void testOneItemInOrder() throws OrderException, SQLException {
		customer.setOrder(order);
		order.addItem(product1, 1);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 1)).thenReturn("inv");
		processOrderImpl.processOrder(ACCOUNT_NUMBER);
		verify(applePayHandler, times(1)).pay("inv", ACCOUNT_EMAIL, 1.0);
		verify(barista, times(1)).prepareItem("abc", 1, ACCOUNT_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 1);
	}

	// Test6
	@Test
	public void testTwoItemsInOrder() throws OrderException, SQLException {
		customer.setOrder(order);
		order.addItem(product1, 1);
		order.addItem(product2, 1);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 3)).thenReturn("inv");
		processOrderImpl.processOrder(ACCOUNT_NUMBER);
		verify(applePayHandler, times(1)).pay("inv", ACCOUNT_EMAIL, 3.0);
		verify(barista, times(1)).prepareItem("abc", 1, ACCOUNT_NAME);
		verify(barista, times(1)).prepareItem("def", 1, ACCOUNT_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 3);
	}

	// Test 7
	@Test
	public void testOneTeaTwoCoffeeItemsInOrder() throws OrderException, SQLException {

	}

	// Test8
	@Test
	public void testOrderPaymentException() throws OrderException, SQLException {
		customer.setOrder(order);
		order.addItem(product1, 1);
		when(orderDAO.findCustomerForId(ACCOUNT_NUMBER)).thenReturn(customer);
		when(invoicer.invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 1)).thenReturn("inv");
		doThrow(OrderPaymentException.class).when(applePayHandler).pay("inv", ACCOUNT_EMAIL, 1.0);
		Throwable exception = assertThrows(OrderPaymentException.class, () -> {
			processOrderImpl.processOrder(ACCOUNT_NUMBER);
		});
		assertEquals("Problem with payment: " + ACCOUNT_NUMBER, exception.getMessage());

		verify(applePayHandler, times(1)).pay("inv", ACCOUNT_EMAIL, 1.0);
		verify(barista, times(0)).prepareItem("abc", 1, ACCOUNT_NAME);
		verify(invoicer, new Times(1)).invoiceCustomer(ACCOUNT_NUMBER, ACCOUNT_EMAIL, 1);
	}

}
