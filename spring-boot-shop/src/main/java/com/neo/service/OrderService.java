package com.neo.service;

import com.neo.model.Order;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrderFromCart(Long cartId);
    Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities);
    Order getOrderById(Long orderId);
    Order updateOrderStatus(Long orderId, Order.OrderStatus status);
    Order payOrder(Long orderId, BigDecimal amount);
    void cancelOrder(Long orderId);
    List<Order> getOrdersByCustomerId(Long customerId);
    List<Order> getOrdersByStatus(Order.OrderStatus status);
    List<Order> getOrdersByCustomerAndStatus(Long customerId, Order.OrderStatus status);
    BigDecimal calculateOrderTotal(Long orderId);
}