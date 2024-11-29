package com.tus.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tus.cart.Cart;
import com.tus.cart.CartItemDTO;
import com.tus.cart.Customer;
import com.tus.exception.CartEmptyException;
import com.tus.exception.CartException;
import com.tus.exception.InventoryException;
import com.tus.exception.PaymentException;
import com.tus.payment.CreditCardStrategy;
import com.tus.payment.PayPalStrategy;
import com.tus.payment.PaymentGateway;
import com.tus.payment.PaymentStrategy;
import com.tus.services.InventoryService;
import com.tus.services.NotificationService;

public class CheckoutServiceImplTest {
	private CheckoutServiceImpl checkoutServiceImpl;
	private Customer customer;
	private Cart cart;
	private long accountNumber = 123L;
	private String customerName = "joe";
	private String customerEmail = "joe@mail.com";
	private String customerAddress = "athlone";
	private String paymentTypePP = "PayPal";
	private String paymentTypeCC = "CreditCard";
	private PaymentStrategy paymentStrategyPP;
	private PaymentStrategy paymentStrategyCC;

	private final InventoryService inventoryService = mock(InventoryService.class);
	private final NotificationService notificationService = mock(NotificationService.class);
	private final PaymentGateway paymentGateway = mock(PaymentGateway.class);

	private final CartItemDTO item1 = new CartItemDTO(123L, "tea", 2.0, 1);
	private final CartItemDTO item2 = new CartItemDTO(1234L, "eeffoc", 3.0, 1);

	@BeforeEach
	public void setUp() {
		checkoutServiceImpl = new CheckoutServiceImpl(inventoryService, notificationService, paymentGateway);
		customer = new Customer();
		cart = new Cart();
		customer.setCart(cart);
		customer.setAccountNumber(accountNumber);
		customer.setAddress(customerAddress);
		customer.setEmail(customerEmail);
		customer.setName(customerName);
		paymentStrategyPP = new PayPalStrategy("joe@mail.com", "abc");
		paymentStrategyCC = new CreditCardStrategy("123", "10/10/10", "333");

	}

	// Test 1
	@Test
	public void testEmptyCartException() throws CartException {
		Throwable exception = assertThrows(CartEmptyException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals(exception.getMessage(), "Cart is Empty : " + accountNumber);
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(paymentGateway, times(0)).processPayPal(accountNumber, customerEmail, customerName);
		verify(notificationService, times(0)).notifyOrderProcessed(cart);
	}

	// Test 2
	@Test
	public void testInventoryNotAvailableException() throws CartException {
		cart.addItem(item1);
		Throwable exception = assertThrows(InventoryException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals(exception.getMessage(),
				"Product " + item1.getProductName() + " is out of stock or doesn't have sufficient quantity.");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(paymentGateway, times(0)).processPayPal(accountNumber, customerEmail, customerName);
		verify(notificationService, times(0)).notifyOrderProcessed(cart);
	}

	// Test 3
	@Test
	public void testPayPalProcessingException() throws CartException {
		cart.addItem(item1);
		customer.setPaymentStrategy(paymentStrategyPP);
		customer.setPaymentType(paymentTypePP);

		when(inventoryService.isAvailable(123L, 1)).thenReturn(true);
		doThrow(new PaymentException("some String")).when(paymentGateway).processPayPal(2, "joe@mail.com", "abc");
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals(exception.getMessage(), "Error processing payment for customer Id " + accountNumber);
		verify(paymentGateway, times(1)).processPayPal(2.0, customerEmail, "abc");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(cart);

	}

	// Test 4
	@Test
	public void testCreditCardProcessingException() throws CartException {
		cart.addItem(item1);
		customer.setPaymentStrategy(paymentStrategyCC);
		customer.setPaymentType(paymentTypeCC);

		when(inventoryService.isAvailable(123L, 1)).thenReturn(true);
		doThrow(new PaymentException("some String")).when(paymentGateway).processCreditCard(2.0, "123", "10/10/10",
				"333");
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals(exception.getMessage(), "Error processing payment for customer Id " + accountNumber);
		verify(paymentGateway, times(1)).processCreditCard(2.0, "123", "10/10/10", "333");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(cart);
	}

	// Test 5
	@Test
	public void testPayPalOneCartItemSuccess() throws CartException {
		cart.addItem(item1);
		customer.setPaymentStrategy(paymentStrategyPP);
		customer.setPaymentType(paymentTypePP);
		when(inventoryService.isAvailable(123L, 1)).thenReturn(true);
		checkoutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processPayPal(2.0, customerEmail, "abc");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);

	}

	// Test 6
	@Test
	public void testCreditCardOneCartItemSuccess() throws CartException {
		cart.addItem(item1);
		customer.setPaymentStrategy(paymentStrategyCC);
		customer.setPaymentType(paymentTypeCC);
		when(inventoryService.isAvailable(123L, 1)).thenReturn(true);
		checkoutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processCreditCard(2.0, "123", "10/10/10", "333");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);

	}

	@Test
	public void testPayPalTwoCartItemSuccess() throws CartException {
		cart.addItem(item1);
		cart.addItem(item2);
		customer.setPaymentStrategy(paymentStrategyPP);
		customer.setPaymentType(paymentTypePP);
		when(inventoryService.isAvailable(123L, 1)).thenReturn(true);
		when(inventoryService.isAvailable(1234L, 1)).thenReturn(true);
		checkoutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processPayPal(5.0, customerEmail, "abc");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(inventoryService, times(1)).deductItem(1234L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);
	}

}
