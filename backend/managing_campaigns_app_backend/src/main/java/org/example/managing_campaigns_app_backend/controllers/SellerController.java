package org.example.managing_campaigns_app_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.dto.SellerDTO;
import org.example.managing_campaigns_app_backend.mappers.SellerMapper;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.example.managing_campaigns_app_backend.services.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
@Validated
public class SellerController {

    private final SellerService sellerService;
    private final SellerMapper sellerMapper;

    @GetMapping
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        List<SellerDTO> sellerDTOs = sellerMapper.toDto(sellers);
        return ResponseEntity.ok(sellerDTOs);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long sellerId) {
        Seller seller = sellerService.getSellerById(sellerId);
        SellerDTO sellerDTO = sellerMapper.toDto(seller);
        return ResponseEntity.ok(sellerDTO);
    }

    @PostMapping
    public ResponseEntity<SellerDTO> createSeller(@RequestBody @Valid SellerDTO sellerDTO) {
        Seller seller = sellerMapper.toEntity(sellerDTO);
        Seller createdSeller = sellerService.createSeller(seller);
        return ResponseEntity.ok(sellerMapper.toDto(createdSeller));
    }

    @PutMapping("/{sellerId}")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable Long sellerId, @RequestBody @Valid SellerDTO sellerDTO) {
        Seller seller = sellerMapper.toEntity(sellerDTO);
        Seller updatedSeller = sellerService.updateSeller(sellerId, seller);
        return ResponseEntity.ok(sellerMapper.toDto(updatedSeller));
    }

    @DeleteMapping("/{sellerId}")
    public ResponseEntity<?> deleteSeller(@PathVariable Long sellerId) {
        sellerService.deleteSeller(sellerId);
        return ResponseEntity.ok("Seller deleted");
    }
}
