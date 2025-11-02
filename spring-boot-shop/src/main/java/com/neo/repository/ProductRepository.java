package com.neo.repository;

import com.neo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(Product.ProductStatus status);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySubCategoryId(Long subCategoryId);
    List<Product> findByNameContainingIgnoreCase(String name);
}