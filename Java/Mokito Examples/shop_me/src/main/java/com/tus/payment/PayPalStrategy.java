package com.tus.payment;

public class PayPalStrategy extends PaymentStrategy {
	private String email;
    private String password; // Ideally, you'd use OAuth tokens or other secure means.

    public PayPalStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
