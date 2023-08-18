package com.xcale.cart.repository;

import com.xcale.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByCartId(String cartId);

    void deleteAllByLastUsedBefore(LocalDateTime date);
}
