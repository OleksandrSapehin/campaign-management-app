package org.example.managing_campaigns_app_backend.service;

import org.example.managing_campaigns_app_backend.models.Seller;
import org.example.managing_campaigns_app_backend.repository.SellerRepository;
import org.example.managing_campaigns_app_backend.services.impl.SellerServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Test
    void testGetSellerById_Success() {
        Long sellerId = 1L;
        Seller seller = new Seller();
        seller.setId(sellerId);

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        Seller result = sellerService.getSellerById(sellerId);

        assertNotNull(result);
        assertEquals(sellerId, result.getId());
    }

    @Test
    void testGetSellerById_NotFound() {
        Long sellerId = 1L;

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            sellerService.getSellerById(sellerId);
        });
    }

    @Test
    void testCreateSeller_Success() {
        Seller seller = new Seller();
        seller.setUsername("testuser");
        seller.setEmail("test@example.com");
        seller.setBalance(new BigDecimal("1000.00"));

        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        Seller result = sellerService.createSeller(seller);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(new BigDecimal("1000.00"), result.getBalance());
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testUpdateSeller_Success() {
        Long sellerId = 1L;

        Seller existingSeller = new Seller();
        existingSeller.setId(sellerId);
        existingSeller.setUsername("olduser");
        existingSeller.setEmail("old@example.com");
        existingSeller.setBalance(new BigDecimal("500.00"));

        Seller updatedSeller = new Seller();
        updatedSeller.setUsername("newuser");
        updatedSeller.setEmail("new@example.com");
        updatedSeller.setBalance(new BigDecimal("1500.00"));

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(existingSeller));
        when(sellerRepository.save(any(Seller.class))).thenReturn(updatedSeller);

        Seller result = sellerService.updateSeller(sellerId, updatedSeller);

        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
    }

    @Test
    void testUpdateSeller_NotFound() {
        Long sellerId = 1L;

        Seller updatedSeller = new Seller();
        updatedSeller.setUsername("newuser");
        updatedSeller.setEmail("new@example.com");
        updatedSeller.setBalance(new BigDecimal("1500.00"));

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            sellerService.updateSeller(sellerId, updatedSeller);
        });

        verify(sellerRepository, never()).save(any(Seller.class));
    }

    @Test
    void testUpdateSellerBalance_Success() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setBalance(new BigDecimal("1000.00"));

        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        sellerService.updateSellerBalance(seller, new BigDecimal("500.00"));

        assertEquals(new BigDecimal("500.00"), seller.getBalance());
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testDeleteSeller_Success() {
        Long sellerId = 1L;

        sellerService.deleteSeller(sellerId);

        verify(sellerRepository, times(1)).deleteById(sellerId);
    }

    @Test
    void testGetAllSellers_Success() {
        List<Seller> sellers = List.of(new Seller(), new Seller());

        when(sellerRepository.findAll()).thenReturn(sellers);

        List<Seller> result = sellerService.getAllSellers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
