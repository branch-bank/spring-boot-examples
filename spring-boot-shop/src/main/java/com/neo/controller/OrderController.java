package com.neo.controller;

import com.neo.model.Order;
import com.neo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 从购物车创建订单
     * @param cartId 购物车ID
     * @return 创建的订单
     */
    @PostMapping("/from-cart/{cartId}")
    public ResponseEntity<Order> createOrderFromCart(@PathVariable Long cartId) {
        try {
            Order order = orderService.createOrderFromCart(cartId);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 直接创建订单
     * @param customerId 顾客ID
     * @param productIds 商品ID列表
     * @param quantities 商品数量列表
     * @return 创建的订单
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long customerId,
            @RequestParam List<Long> productIds,
            @RequestParam List<Integer> quantities) {
        try {
            Order order = orderService.createOrder(customerId, productIds, quantities);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 获取订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 新状态
     * @return 更新后的订单
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 支付订单
     * @param orderId 订单ID
     * @param amount 支付金额
     * @return 支付后的订单
     */
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Order> payOrder(
            @PathVariable Long orderId,
            @RequestParam BigDecimal amount) {
        try {
            Order order = orderService.payOrder(orderId, amount);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 取消订单
     * @param orderId 订单ID
     * @return 响应状态
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取顾客的所有订单
     * @param customerId 顾客ID
     * @return 订单列表
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 获取特定状态的订单
     * @param status 订单状态
     * @return 订单列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 获取顾客特定状态的订单
     * @param customerId 顾客ID
     * @param status 订单状态
     * @return 订单列表
     */
    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByCustomerAndStatus(
            @PathVariable Long customerId,
            @PathVariable Order.OrderStatus status) {
        List<Order> orders = orderService.getOrdersByCustomerAndStatus(customerId, status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 计算订单总额
     * @param orderId 订单ID
     * @return 订单总额
     */
    @GetMapping("/{orderId}/total")
    public ResponseEntity<BigDecimal> calculateOrderTotal(@PathVariable Long orderId) {
        BigDecimal total = orderService.calculateOrderTotal(orderId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
}