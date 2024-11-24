package com.tus.cart;
public class CartItemDTO {

private Long productId;
private String productName;
private Double productPrice;
private Integer quantity;


public CartItemDTO(Long productId, String productName, Double productPrice, Integer quantity) {
    this.productId = productId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.quantity = quantity;
}

// Getters and Setters

public Long getProductId() {
    return productId;
}


public String getProductName() {
    return productName;
}

public Double getProductPrice() {
    return productPrice;
}

public Integer getQuantity() {
    return quantity;
}

}