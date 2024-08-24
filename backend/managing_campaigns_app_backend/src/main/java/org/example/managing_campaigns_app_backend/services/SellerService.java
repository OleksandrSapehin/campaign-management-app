package org.example.managing_campaigns_app_backend.services;

import org.example.managing_campaigns_app_backend.dto.SellerDTO;
import org.example.managing_campaigns_app_backend.models.Seller;

import java.math.BigDecimal;
import java.util.List;

public interface SellerService {

    Seller getSellerById(Long sellerId);
    Seller createSeller(Seller seller);
    Seller updateSeller(Long sellerId, Seller seller);
    void updateSellerBalance(Seller seller, BigDecimal amount);
    void deleteSeller(Long sellerId);
    List<Seller> getAllSellers();
}
