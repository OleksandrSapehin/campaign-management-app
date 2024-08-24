package org.example.managing_campaigns_app_backend.service;

import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.repository.ProductRepository;
import org.example.managing_campaigns_app_backend.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetProductById_Success() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById(productId);
        });
    }

    @Test
    void testCreateProduct_Success() {
        Product product = new Product();
        product.setName("Test Product");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Success() {
        Long productId = 1L;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");

        Product updatedProduct = new Product();
        updatedProduct.setName("New Name");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    @Test
    void testUpdateProduct_NotFound() {
        Long productId = 1L;

        Product updatedProduct = new Product();
        updatedProduct.setName("New Name");

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(productId, updatedProduct);
        });

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> products = List.of(new Product(), new Product());

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
