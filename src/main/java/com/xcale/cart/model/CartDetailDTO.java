package com.xcale.cart.model;

public class CartDetailDTO {

    private Long productId;

    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartDetailDTO{" +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
