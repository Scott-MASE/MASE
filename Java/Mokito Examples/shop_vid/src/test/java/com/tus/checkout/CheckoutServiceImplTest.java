package com.tus.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.tus.services.InventoryService;
import com.tus.services.NotificationService;

public class CheckoutServiceImplTest {
	private Customer customer;
	private Cart cart;
	static final long ACCOUNT_NUMBER = 123456L;
	private final PaymentGateway paymentGateway = mock(PaymentGateway.class);
	private final InventoryService inventoryService = mock(InventoryService.class);
	private final NotificationService notificationService = mock(NotificationService.class);
	private CheckoutServiceImpl checkOutServiceImpl;
	private CartItemDTO cartItemDTO1;
	private PayPalStrategy payPalStrategy;
	private CreditCardStrategy creditCardStrategy;

	@BeforeEach
	public void setUp() {
		customer = new Customer();
		cart = new Cart();
		cartItemDTO1 = new CartItemDTO(123L, "Big TV", 500.0, 1);
		customer.setCart(cart);
		customer.setAccountNumber(ACCOUNT_NUMBER);
		customer.setAddress("Athlone");
		customer.setName("Joe");
		customer.setCart(cart);
		customer.setPaymentType("PayPal");
		payPalStrategy = new PayPalStrategy("joe@email.com","abc");
		customer.setPaymentStrategy(payPalStrategy);
		checkOutServiceImpl = new CheckoutServiceImpl(inventoryService, notificationService, paymentGateway);

	}

	// Test 1
	@Test
	void testEmptyCartException() throws CartException {
		Throwable exception = assertThrows(CartEmptyException.class, () -> {
			checkOutServiceImpl.checkout(customer);
		});
		assertEquals("Cart is Empty : 123456", exception.getMessage());
		verify(paymentGateway, times(0)).processPayPal(anyLong(), anyString(), anyString());
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}

	// Test 2
	@Test
	public void testInventoryNotAvailableException() throws CartException {
		cart.addItem(cartItemDTO1);
		Throwable exception = assertThrows(InventoryException.class, () -> {
			checkOutServiceImpl.checkout(customer);
		});
		assertEquals("Product Big TV is out of stock or doesn't have sufficient quantity.", exception.getMessage());
		verify(paymentGateway, times(0)).processPayPal(anyLong(), anyString(), anyString());
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}

	// Test 3
	@Test
	public void testPayPalProcessingException() throws CartException {
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(123L,1)).thenReturn(true);
		doThrow(new PaymentException("some String")).when(paymentGateway).processPayPal(500, "joe@email.com", "abc");
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkOutServiceImpl.checkout(customer);
		});
		assertEquals("Error processing payment for customer Id 123456", exception.getMessage());
		verify(paymentGateway, times(1)).processPayPal(500, "joe@email.com", "abc");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}

	// Test 4
	@Test
	public void testCreditCardProcessingException() throws CartException {
		customer.setPaymentType("CreditCard");
		creditCardStrategy = new CreditCardStrategy("1234-5678", "12/03/2023", "017");
		customer.setPaymentStrategy(creditCardStrategy);
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(123L,1)).thenReturn(true);
		doThrow(new PaymentException("some String")).when(paymentGateway).processCreditCard(500.0, "1234-5678","12/03/2023", "017" );
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkOutServiceImpl.checkout(customer);
		});
		assertEquals("Error processing payment for customer Id 123456", exception.getMessage());
		verify(paymentGateway, times(1)).processCreditCard(500.0, "1234-5678","12/03/2023", "017");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}

	// Test 5
	@Test
	public void testPayPalOneCartItemSuccess() throws CartException {
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(123L,1)).thenReturn(true);
		checkOutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processPayPal(500, "joe@email.com", "abc");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);

	}

	// Test 6
	@Test
	public void testCreditCardOneCartItemSuccess() throws CartException {
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(123L,1)).thenReturn(true);
		customer.setPaymentType("CreditCard");
		creditCardStrategy = new CreditCardStrategy("1234-5678", "12/03/2023", "017");
		customer.setPaymentStrategy(creditCardStrategy);
		checkOutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processCreditCard(500.0, "1234-5678","12/03/2023", "017");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);
	}

	@Test
	public void testPayPalTwoCartItemSuccess() throws CartException {
		CartItemDTO cartItemDTO2 = new CartItemDTO(1234L, "Small TV", 100.0, 1);
		cart.addItem(cartItemDTO1);
		cart.addItem(cartItemDTO2);
		when(inventoryService.isAvailable(123L,1)).thenReturn(true);
		when(inventoryService.isAvailable(1234L,1)).thenReturn(true);
		checkOutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processPayPal(600, "joe@email.com", "abc");
		verify(inventoryService, times(1)).deductItem(123L, 1);
		verify(inventoryService, times(1)).deductItem(1234L, 1);
		verify(notificationService, times(1)).notifyOrderProcessed(cart);

	}
}
