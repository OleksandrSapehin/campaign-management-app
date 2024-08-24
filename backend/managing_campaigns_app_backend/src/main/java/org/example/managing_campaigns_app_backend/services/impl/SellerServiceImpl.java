package org.example.managing_campaigns_app_backend.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.example.managing_campaigns_app_backend.repository.SellerRepository;
import org.example.managing_campaigns_app_backend.services.SellerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("Seller not found"));
    }

    @Override
    @Transactional
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public Seller updateSeller(Long sellerId, Seller seller) {
        Seller existingSeller = sellerRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        existingSeller.setUsername(seller.getUsername());
        existingSeller.setEmail(seller.getEmail());
        existingSeller.setBalance(seller.getBalance());

        return sellerRepository.save(existingSeller);
    }

    @Override
    @Transactional
    public void updateSellerBalance(Seller seller, BigDecimal amount) {
        seller.setBalance(seller.getBalance().subtract(amount));
        sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public void deleteSeller(Long sellerId) {
        sellerRepository.deleteById(sellerId);
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
}
