package com.tus.checkout;

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

public class CheckoutServiceImpl implements CheckoutService{

    private InventoryService inventoryService;
    private NotificationService notificationService;
    private PaymentGateway paymentGateway;
    private Cart cart;

    public CheckoutServiceImpl(InventoryService inventoryService, NotificationService notificationService,
    			PaymentGateway paymentGateway) {
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
        this.paymentGateway = paymentGateway;
    }

    public void checkout(Customer customer) throws CartException {
    	cart = customer.getCart();
        if (cart.getItems().isEmpty()) {
        	throw new CartEmptyException(customer.getAccountNumber());
        }
        
        for (CartItemDTO item : cart.getItems()) {
            if (!inventoryService.isAvailable(item.getProductId(), item.getQuantity())) {
            	throw new InventoryException("Product " + item.getProductName() + " is out of stock or doesn't have sufficient quantity.");
            }
        }
        final PaymentStrategy paymentStrategy = customer.getPaymentStrategy();

        double totalAmount = cart.calculateTotal();
        try {
        if (customer.getPaymentType().equals("CreditCard")) {
        	final CreditCardStrategy creditCardStrategy = ((CreditCardStrategy) paymentStrategy);
        	paymentGateway.processCreditCard(totalAmount, creditCardStrategy.getCardNumber(), creditCardStrategy.getExpiryDate(), creditCardStrategy.getCvv());
        }else
        	if (customer.getPaymentType().equals("PayPal")) {
            	final PayPalStrategy payPalStrategy = ((PayPalStrategy) paymentStrategy);
            	paymentGateway.processPayPal(totalAmount, payPalStrategy.getEmail(),payPalStrategy.getPassword());
        }}
        catch (PaymentException e) {
        	throw new PaymentException("Error processing payment for customer Id "+customer.getAccountNumber());
        }

        for (CartItemDTO item : cart.getItems()) {
            inventoryService.deductItem(item.getProductId(), item.getQuantity());
        }
 
        notificationService.notifyOrderProcessed(cart);
    }

}