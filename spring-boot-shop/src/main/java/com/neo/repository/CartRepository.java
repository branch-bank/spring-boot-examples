package com.neo.repository;

import com.neo.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCustomerId(Long customerId);
    Optional<Cart> findByCustomerIdAndStatus(Long customerId, Cart.CartStatus status);
}