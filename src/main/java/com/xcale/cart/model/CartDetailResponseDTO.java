package com.xcale.cart.model;


import java.io.Serializable;

/**
 * Cart DTO detail class used by create cart item request
 */
public class CartDetailResponseDTO implements Serializable {

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
