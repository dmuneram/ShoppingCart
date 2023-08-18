package com.xcale.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Cart implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String cartId;

    @Column
    private BigDecimal cartTotal;

    @JsonIgnore
    private LocalDateTime lastUsed;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = CartDetail.class)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Set<CartDetail> cartItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }

    public Set<CartDetail> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartDetail> cartItems) {
        this.cartItems = cartItems;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }
}
