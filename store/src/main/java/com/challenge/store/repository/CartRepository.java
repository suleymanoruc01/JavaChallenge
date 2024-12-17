package com.challenge.store.repository;

import com.challenge.store.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCustomerId(Long customerId);
    Cart getById(Long cartId);
    Cart getCartByCustomerId(Long customerId);
}
