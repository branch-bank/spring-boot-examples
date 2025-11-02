package com.neo.controller;

import com.neo.model.Cart;
import com.neo.model.CartItem;
import com.neo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 创建购物车
     * @param customerId 顾客ID
     * @return 创建的购物车
     */
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestParam Long customerId) {
        Cart cart = cartService.createCart(customerId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 获取购物车详情
     * @param cartId 购物车ID
     * @return 购物车详情
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 获取顾客的活跃购物车
     * @param customerId 顾客ID
     * @return 活跃购物车
     */
    @GetMapping("/active/{customerId}")
    public ResponseEntity<Cart> getActiveCartByCustomerId(@PathVariable Long customerId) {
        Cart cart = cartService.getActiveCartByCustomerId(customerId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 添加商品到购物车
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 添加的购物车商品项
     */
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addItemToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.addItemToCart(cartId, productId, quantity);
            if (cartItem != null) {
                return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 更新购物车商品项数量
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @param quantity 新数量
     * @return 更新后的购物车商品项
     */
    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.updateCartItemQuantity(cartId, productId, quantity);
            if (cartItem != null) {
                return new ResponseEntity<>(cartItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 从购物车移除商品
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @return 响应状态
     */
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        cartService.removeItemFromCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 清空购物车
     * @param cartId 购物车ID
     * @return 响应状态
     */
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 计算购物车总价
     * @param cartId 购物车ID
     * @return 购物车总价
     */
    @GetMapping("/{cartId}/total")
    public ResponseEntity<BigDecimal> calculateCartTotal(@PathVariable Long cartId) {
        BigDecimal total = cartService.calculateCartTotal(cartId);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    /**
     * 标记购物车为已支付
     * @param cartId 购物车ID
     * @return 响应状态
     */
    @PutMapping("/{cartId}/mark-paid")
    public ResponseEntity<Void> markCartAsPaid(@PathVariable Long cartId) {
        cartService.markCartAsPaid(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取顾客的所有购物车
     * @param customerId 顾客ID
     * @return 购物车列表
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Cart>> getCartsByCustomerId(@PathVariable Long customerId) {
        List<Cart> carts = cartService.getCartsByCustomerId(customerId);
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }
}