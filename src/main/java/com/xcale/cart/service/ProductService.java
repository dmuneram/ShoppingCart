package com.xcale.cart.service;

import com.xcale.cart.entity.Product;
import com.xcale.cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;


    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {

        return this.repository.findAll();
    }
}
