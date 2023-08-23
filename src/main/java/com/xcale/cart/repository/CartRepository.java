package com.xcale.cart.repository;

import com.xcale.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Cart repository interface used to query Cart table
 * Custom queries can be added here
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * fetch cart using cart UUID
     * @param cartId uuid
     * @return cart
     */
    Optional<Cart> findCartByCartId(String cartId);

    /**
     * delete all the cart before parameter date
     * @param date LocalDateTime
     */
    void deleteAllByLastUsedBefore(LocalDateTime date);
}
