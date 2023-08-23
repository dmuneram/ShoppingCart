package com.xcale.cart.model;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

/**
 * Cart DTO class used by create cart request
 */
public class CartRequestDTO implements Serializable {

    private String id;

    @NotEmpty
    private Set<CartDetailRequestDTO> cartItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<CartDetailRequestDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartDetailRequestDTO> cartItems) {
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
