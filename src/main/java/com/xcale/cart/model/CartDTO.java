package com.xcale.cart.model;

import java.util.Set;

public class CartDTO {

    private String id;

    private Set<CartDetailDTO> cartItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<CartDetailDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartDetailDTO> cartItems) {
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
