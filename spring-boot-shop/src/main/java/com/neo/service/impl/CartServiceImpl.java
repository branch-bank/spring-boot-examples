package com.neo.service.impl;

import com.neo.model.Cart;
import com.neo.model.CartItem;
import com.neo.model.Product;
import com.neo.model.Customer;
import com.neo.repository.CartRepository;
import com.neo.repository.CartItemRepository;
import com.neo.repository.ProductRepository;
import com.neo.repository.CustomerRepository;
import com.neo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Cart createCart(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalAmount(BigDecimal.ZERO);
            cart.setStatus(Cart.CartStatus.UNPAID);
            return cartRepository.save(cart);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCartById(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        return cartOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getActiveCartByCustomerId(Long customerId) {
        Optional<Cart> cartOptional = cartRepository.findByCustomerIdAndStatus(customerId, Cart.CartStatus.UNPAID);
        return cartOptional.orElse(null);
    }

    @Override
    public CartItem addItemToCart(Long cartId, Long productId, Integer quantity) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (cartOptional.isPresent() && productOptional.isPresent()) {
            Cart cart = cartOptional.get();
            Product product = productOptional.get();

            // Check if product is in stock
            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Check if item already exists in cart
            Optional<CartItem> existingItemOptional = cartItemRepository.findByCartIdAndProductId(cartId, productId);
            CartItem cartItem;

            if (existingItemOptional.isPresent()) {
                cartItem = existingItemOptional.get();
                int newQuantity = cartItem.getQuantity() + quantity;
                if (product.getStockQuantity() < newQuantity) {
                    throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
                }
                cartItem.setQuantity(newQuantity);
            } else {
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
            }

            // Calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
            cartItem.setSubtotal(subtotal);

            // Save cart item
            CartItem savedItem = cartItemRepository.save(cartItem);

            // Update cart total
            updateCartTotal(cartId);

            return savedItem;
        }
        return null;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartId, Long productId, Integer quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Product product = cartItem.getProduct();

            // Check if product is in stock
            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Update quantity and subtotal
            cartItem.setQuantity(quantity);
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
            cartItem.setSubtotal(subtotal);

            // Save cart item
            CartItem updatedItem = cartItemRepository.save(cartItem);

            // Update cart total
            updateCartTotal(cartId);

            return updatedItem;
        }
        return null;
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartIdAndProductId(cartId, productId);
        cartItemOptional.ifPresent(cartItem -> {
            cartItemRepository.delete(cartItem);
            updateCartTotal(cartId);
        });
    }

    @Override
    public void clearCart(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        cartItemRepository.deleteAll(cartItems);
        updateCartTotal(cartId);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCartTotal(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void markCartAsPaid(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        cartOptional.ifPresent(cart -> {
            cart.setStatus(Cart.CartStatus.PAID);
            cartRepository.save(cart);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> getCartsByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    private void updateCartTotal(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        cartOptional.ifPresent(cart -> {
            BigDecimal total = calculateCartTotal(cartId);
            cart.setTotalAmount(total);
            cartRepository.save(cart);
        });
    }
}