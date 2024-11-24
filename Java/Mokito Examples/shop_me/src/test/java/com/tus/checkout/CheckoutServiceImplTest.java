package com.tus.checkout;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.tus.cart.Cart;
import com.tus.cart.CartItemDTO;
import com.tus.cart.Customer;
import com.tus.payment.CreditCardStrategy;
import com.tus.payment.PayPalStrategy;
import com.tus.services.InventoryService;
import com.tus.services.NotificationService;
import com.tus.payment.PaymentGateway;
import com.tus.payment.PaymentStrategy;
import com.tus.exception.CartException;
import com.tus.exception.InventoryException;
import com.tus.exception.PaymentException;
import com.tus.exception.CartEmptyException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;

public class CheckoutServiceImplTest {
	private CheckoutServiceImpl checkoutServiceImpl;
	private Customer customer;
	private Cart cart;

	private final long ACCOUNT_NUMBER = 123L;
	private final String ACCOUNT_NAME = "joe";
	private final String ACCOUNT_EMAIL = "joe@mail.com";
	private final String ACCOUNT_ADDRESS = "Athlone";
	private final String PAYMENT_TYPE_PAYPAL = "PayPal";
	private final String PAYMENT_TYPE_CREDIT = "CreditCard";
	private final PayPalStrategy palStrategy = new PayPalStrategy(ACCOUNT_EMAIL, "abc");
	private final CreditCardStrategy creditCardStrategy = new CreditCardStrategy("1234", "monday", "333");
	private final CartItemDTO cartItemDTO1 = new CartItemDTO(1233L, "tea", 2.0, 1);


	private final InventoryService inventoryService = mock(InventoryService.class);
	private final NotificationService notificationService = mock(NotificationService.class);
	private final PaymentGateway paymentGateway = mock(PaymentGateway.class);

	

	@BeforeEach
	public void setUp() {
		checkoutServiceImpl = new CheckoutServiceImpl(inventoryService, notificationService, paymentGateway);
		customer = new Customer();
		cart = new Cart();
		customer.setCart(cart);
		customer.setAccountNumber(ACCOUNT_NUMBER);
		customer.setAddress(ACCOUNT_ADDRESS);
		customer.setEmail(ACCOUNT_EMAIL);
		customer.setName(ACCOUNT_NAME);
	}
	//Test 1
	@Test
	public void testEmptyCartException() throws CartException {
		Throwable exception = assertThrows(CartEmptyException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals("Cart is Empty : " + ACCOUNT_NUMBER, exception.getMessage());
		verify(paymentGateway, times(0)).processPayPal(anyDouble(), anyString(), anyString());
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());	
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}
	//Test 2
	@Test
	public void testInventoryNotAvailableException() throws CartException {
		cart.addItem(cartItemDTO1);
		Throwable exception = assertThrows(InventoryException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals("Product " + cartItemDTO1.getProductName() + " is out of stock or doesn't have sufficient quantity.", exception.getMessage());
		verify(paymentGateway, times(0)).processPayPal(anyDouble(), anyString(), anyString());
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());	
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}
	
	//Test 3
	@Test
	public void testPayPalProcessingException() throws CartException {
		customer.setPaymentType(PAYMENT_TYPE_PAYPAL);
		customer.setPaymentStrategy(palStrategy);
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(1233L, 1)).thenReturn(true);
		doThrow(new PaymentException("huh")).when(paymentGateway).processPayPal(2, ACCOUNT_EMAIL,"abc");
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals("Error processing payment for customer Id " + ACCOUNT_NUMBER, exception.getMessage());
		verify(paymentGateway, times(1)).processPayPal(2, ACCOUNT_EMAIL,"abc");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());	
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}
	
	//Test 4
	@Test
	public void testCreditCardProcessingException() throws CartException {
		customer.setPaymentType(PAYMENT_TYPE_CREDIT);
		customer.setPaymentStrategy(creditCardStrategy);
		cart.addItem(cartItemDTO1);	
		when(inventoryService.isAvailable(1233L, 1)).thenReturn(true);
		doThrow(new PaymentException("huh")).when(paymentGateway).processCreditCard(2.0, "1234","monday", "333");
		Throwable exception = assertThrows(PaymentException.class, () -> {
			checkoutServiceImpl.checkout(customer);
		});
		assertEquals("Error processing payment for customer Id " + ACCOUNT_NUMBER, exception.getMessage());
		verify(paymentGateway, times(1)).processCreditCard(2.0, "1234","monday", "333");
		verify(inventoryService, times(0)).deductItem(anyLong(), anyInt());	
		verify(notificationService, times(0)).notifyOrderProcessed(any());
	}
		
	//Test 5
	@Test
	public void testPayPalOneCartItemSuccess() throws CartException {
		customer.setPaymentType(PAYMENT_TYPE_PAYPAL);
		customer.setPaymentStrategy(palStrategy);
		cart.addItem(cartItemDTO1);
		when(inventoryService.isAvailable(1233L, 1)).thenReturn(true);
		checkoutServiceImpl.checkout(customer);
		verify(paymentGateway, times(1)).processPayPal(2, ACCOUNT_EMAIL,"abc");
		verify(inventoryService, times(1)).deductItem(1233L, 1);	
		verify(notificationService, times(1)).notifyOrderProcessed(cart);
	}
	//Test 6
	@Test
	public void testCreditCardOneCartItemSuccess() throws CartException {
					
	}
				
	@Test
	public void testPayPalTwoCartItemSuccess() throws CartException {
					
	}
}
