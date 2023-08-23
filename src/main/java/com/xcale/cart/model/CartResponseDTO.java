package com.xcale.cart.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Cart DTO class used as response
 */
public class CartResponseDTO implements Serializable {

    private String id;

    private BigDecimal cartTotal;

    private Set<CartDetailResponseDTO> cartItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

    public Set<CartDetailResponseDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartDetailResponseDTO> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "CartRequest{" +
                "id=" + id +
                ", cartItems=" + cartItems +
                '}';
    }
}
