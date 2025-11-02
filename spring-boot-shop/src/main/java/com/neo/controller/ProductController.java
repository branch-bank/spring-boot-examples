package com.neo.controller;

import com.neo.model.Product;
import com.neo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增商品
     * @param product 商品信息
     * @return 新增的商品
     */
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * 更新商品
     * @param productId 商品ID
     * @param product 新的商品信息
     * @return 更新后的商品
     */
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 删除商品
     * @param productId 商品ID
     * @return 响应状态
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取商品详情
     * @param productId 商品ID
     * @return 商品详情
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 获取所有商品
     * @return 商品列表
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 按状态获取商品
     * @param status 商品状态
     * @return 商品列表
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Product>> getProductsByStatus(@PathVariable Product.ProductStatus status) {
        List<Product> products = productService.getProductsByStatus(status);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 按分类获取商品
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 按子分类获取商品
     * @param subCategoryId 子分类ID
     * @return 商品列表
     */
    @GetMapping("/subcategory/{subCategoryId}")
    public ResponseEntity<List<Product>> getProductsBySubCategoryId(@PathVariable Long subCategoryId) {
        List<Product> products = productService.getProductsBySubCategoryId(subCategoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 搜索商品
     * @param name 商品名称关键词
     * @return 商品列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 更新商品库存
     * @param productId 商品ID
     * @param quantity 新库存数量
     * @return 响应状态
     */
    @PutMapping("/{productId}/stock")
    public ResponseEntity<Void> updateProductStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        productService.updateProductStock(productId, quantity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 更新商品状态
     * @param productId 商品ID
     * @param status 新状态
     * @return 响应状态
     */
    @PutMapping("/{productId}/status")
    public ResponseEntity<Void> updateProductStatus(
            @PathVariable Long productId,
            @RequestParam Product.ProductStatus status) {
        productService.updateProductStatus(productId, status);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}