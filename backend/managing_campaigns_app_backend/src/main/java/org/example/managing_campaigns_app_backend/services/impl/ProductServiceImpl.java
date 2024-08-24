package org.example.managing_campaigns_app_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.repository.ProductRepository;
import org.example.managing_campaigns_app_backend.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existingProduct.setName(product.getName());

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
