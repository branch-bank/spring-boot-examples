package com.neo.service.impl;

import com.neo.model.Product;
import com.neo.repository.ProductRepository;
import com.neo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStatus(product.getStatus());
            existingProduct.setStockQuantity(product.getStockQuantity());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setSubCategory(product.getSubCategory());
            existingProduct.setSpecs(product.getSpecs());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByStatus(Product.ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsBySubCategory(Long subCategoryId) {
        return productRepository.findBySubCategoryId(subCategoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void updateProductStock(Long productId, Integer quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresent(product -> {
            product.setStockQuantity(quantity);
            productRepository.save(product);
        });
    }

    @Override
    public void updateProductStatus(Long productId, Product.ProductStatus status) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresent(product -> {
            product.setStatus(status);
            productRepository.save(product);
        });
    }
}