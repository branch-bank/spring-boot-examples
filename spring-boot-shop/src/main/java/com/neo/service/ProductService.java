package com.neo.service;

import com.neo.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByStatus(Product.ProductStatus status);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsBySubCategory(Long subCategoryId);
    List<Product> searchProductsByName(String name);
    void updateProductStock(Long productId, Integer quantity);
    void updateProductStatus(Long productId, Product.ProductStatus status);
}