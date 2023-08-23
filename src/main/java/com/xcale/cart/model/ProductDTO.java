package com.xcale.cart.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Product DTO to show products information
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String description;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
