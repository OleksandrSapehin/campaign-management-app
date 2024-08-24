package org.example.managing_campaigns_app_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.dto.ProductDTO;
import org.example.managing_campaigns_app_backend.mappers.ProductMapper;
import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = productMapper.toDto(products);
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        ProductDTO productDTO = productMapper.toDto(product);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toDto(createdProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted");
    }
}
