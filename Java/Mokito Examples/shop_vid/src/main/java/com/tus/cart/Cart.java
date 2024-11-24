package com.tus.cart;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItemDTO> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    // Add a new item to the cart or update the quantity if it already exists
    public void addItem(CartItemDTO newItem) {
            items.add(newItem);
    }

    // Calculate the total price of the items in the cart
    public Double calculateTotal() {
        double total = 0.0;

        for (CartItemDTO item : items) {
            total += item.getProductPrice() * item.getQuantity();
        }

        return total;
    }

    // Getters and setters
    public List<CartItemDTO> getItems() {
        return items;
    }

}
