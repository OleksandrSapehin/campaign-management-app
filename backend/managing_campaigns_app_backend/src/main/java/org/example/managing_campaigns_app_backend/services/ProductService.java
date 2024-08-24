package org.example.managing_campaigns_app_backend.services;

import org.example.managing_campaigns_app_backend.dto.ProductDTO;
import org.example.managing_campaigns_app_backend.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long productId);
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product);
    void deleteProduct(Long productId);
    List<Product> getAllProducts();
}
