package com.neo.service.impl;

import com.neo.model.Order;
import com.neo.model.OrderItem;
import com.neo.model.Product;
import com.neo.model.Customer;
import com.neo.model.Cart;
import com.neo.model.CartItem;
import com.neo.repository.OrderRepository;
import com.neo.repository.OrderItemRepository;
import com.neo.repository.ProductRepository;
import com.neo.repository.CustomerRepository;
import com.neo.repository.CartRepository;
import com.neo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Order createOrderFromCart(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (!cartOptional.isPresent()) {
            return null;
        }

        Cart cart = cartOptional.get();
        Customer customer = cart.getCustomer();

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(cart.getTotalAmount());
        order.setPaidAmount(BigDecimal.ZERO);
        order.setStatus(Order.OrderStatus.PENDING_PAYMENT);

        // Save order first to get order ID
        Order savedOrder = orderRepository.save(order);

        // Create order items from cart items
        List<CartItem> cartItems = cart.getItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();

            // Check stock again
            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItems.add(orderItem);

            // Reduce product stock
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
        }

        // Save all order items
        orderItemRepository.saveAll(orderItems);

        // Mark cart as paid
        cart.setStatus(Cart.CartStatus.PAID);
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities) {
        if (productIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Product IDs and quantities must have the same size");
        }

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) {
            return null;
        }

        Customer customer = customerOptional.get();

        // Create new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setPaidAmount(BigDecimal.ZERO);
        order.setStatus(Order.OrderStatus.PENDING_PAYMENT);

        // Calculate total amount and create order items
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            Integer quantity = quantities.get(i);

            Optional<Product> productOptional = productRepository.findById(productId);
            if (!productOptional.isPresent()) {
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }

            Product product = productOptional.get();

            // Check stock
            if (product.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);

            // Calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(new BigDecimal(quantity));
            orderItem.setSubtotal(subtotal);
            orderItems.add(orderItem);

            // Add to total
            totalAmount = totalAmount.add(subtotal);

            // Reduce product stock
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
        }

        // Set total amount and save order
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        // Set order for each order item and save
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(savedOrder);
        }
        orderItemRepository.saveAll(orderItems);

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        return orderOptional.orElse(null);
    }

    @Override
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return null;
        }

        Order order = orderOptional.get();
        order.setStatus(status);

        // Update timestamps based on status
        if (status == Order.OrderStatus.PAID && order.getPaidAt() == null) {
            order.setPaidAt(LocalDateTime.now());
        } else if (status == Order.OrderStatus.CANCELLED || status == Order.OrderStatus.EXPIRED) {
            // Restore stock if order is cancelled or expired
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem orderItem : orderItems) {
                Product product = orderItem.getProduct();
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        return orderRepository.save(order);
    }

    @Override
    public Order payOrder(Long orderId, BigDecimal amount) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return null;
        }

        Order order = orderOptional.get();

        // Check if order is already paid or cancelled
        if (order.getStatus() != Order.OrderStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("Order is not in pending payment status");
        }

        // Check if payment amount is sufficient
        if (amount.compareTo(order.getTotalAmount()) < 0) {
            throw new IllegalArgumentException("Payment amount is insufficient");
        }

        // Update order status and payment information
        order.setPaidAmount(amount);
        order.setStatus(Order.OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        order.setCompletedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            updateOrderStatus(orderId, Order.OrderStatus.CANCELLED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerAndStatus(Long customerId, Order.OrderStatus status) {
        return orderRepository.findByCustomerIdAndStatus(customerId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateOrderTotal(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return BigDecimal.ZERO;
        }

        Order order = orderOptional.get();
        return order.getItems().stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}