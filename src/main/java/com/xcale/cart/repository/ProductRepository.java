package com.xcale.cart.repository;

import com.xcale.cart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Product repository interface used to query Product table
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
