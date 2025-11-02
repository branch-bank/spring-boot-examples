package com.neo.service;

import com.neo.model.Cart;
import com.neo.model.CartItem;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    Cart createCart(Long customerId);
    Cart getCartById(Long cartId);
    Cart getActiveCartByCustomerId(Long customerId);
    CartItem addItemToCart(Long cartId, Long productId, Integer quantity);
    CartItem updateCartItemQuantity(Long cartId, Long productId, Integer quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void clearCart(Long cartId);
    BigDecimal calculateCartTotal(Long cartId);
    void markCartAsPaid(Long cartId);
    List<Cart> getCartsByCustomerId(Long customerId);
}