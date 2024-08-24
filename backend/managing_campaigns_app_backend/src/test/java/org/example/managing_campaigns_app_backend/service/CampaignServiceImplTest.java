package org.example.managing_campaigns_app_backend.service;

import org.example.managing_campaigns_app_backend.models.Campaign;
import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.example.managing_campaigns_app_backend.repository.CampaignRepository;
import org.example.managing_campaigns_app_backend.services.ProductService;
import org.example.managing_campaigns_app_backend.services.SellerService;
import org.example.managing_campaigns_app_backend.services.impl.CampaignServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private ProductService productService;

    @Mock
    private SellerService sellerService;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Test
    void testCreateCampaign_Success() {
        Long productId = 1L;
        Long sellerId = 1L;

        Product product = new Product();
        product.setId(productId);

        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setBalance(new BigDecimal("1000.00"));

        Campaign campaign = new Campaign();
        campaign.setCampaignFund(new BigDecimal("500.00"));
        campaign.setProduct(product);
        campaign.setSeller(seller);

        when(productService.getProductById(productId)).thenReturn(product);
        when(sellerService.getSellerById(sellerId)).thenReturn(seller);
        when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);

        Campaign result = campaignService.createCampaign(campaign, productId, sellerId);

        assertNotNull(result);
        assertEquals(productId, result.getProduct().getId());
        assertEquals(sellerId, result.getSeller().getId());
        assertEquals(new BigDecimal("500.00"), result.getCampaignFund());
    }

    @Test
    void testCreateCampaign_InsufficientFunds() {
        Long productId = 1L;
        Long sellerId = 1L;

        Product product = new Product();
        product.setId(productId);

        Seller seller = new Seller();
        seller.setId(sellerId);
        seller.setBalance(new BigDecimal("100.00"));

        Campaign campaign = new Campaign();
        campaign.setCampaignFund(new BigDecimal("500.00"));
        campaign.setProduct(product);
        campaign.setSeller(seller);

        when(productService.getProductById(productId)).thenReturn(product);
        when(sellerService.getSellerById(sellerId)).thenReturn(seller);

        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.createCampaign(campaign, productId, sellerId);
        });

        verify(campaignRepository, never()).save(any(Campaign.class));
    }

    @Test
    void testGetCampaignsForProduct_Success() {
        Long productId = 1L;
        List<Campaign> campaigns = List.of(new Campaign(), new Campaign());

        when(campaignRepository.findByProductId(productId)).thenReturn(campaigns);

        List<Campaign> result = campaignService.getCampaignsForProduct(productId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateCampaign_Success() {
        Long campaignId = 1L;

        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setCampaignFund(new BigDecimal("300.00"));

        Campaign updatedCampaign = new Campaign();
        updatedCampaign.setCampaignFund(new BigDecimal("400.00"));

        Seller seller = new Seller();
        seller.setBalance(new BigDecimal("1000.00"));
        existingCampaign.setSeller(seller);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(updatedCampaign);

        Campaign result = campaignService.updateCampaign(campaignId, updatedCampaign);

        assertNotNull(result);
        assertEquals(new BigDecimal("400.00"), result.getCampaignFund());
        verify(sellerService).updateSellerBalance(seller, new BigDecimal("100.00"));
    }

    @Test
    void testUpdateCampaign_InsufficientFunds() {
        Long campaignId = 1L;

        Campaign existingCampaign = new Campaign();
        existingCampaign.setId(campaignId);
        existingCampaign.setCampaignFund(new BigDecimal("300.00"));

        Campaign updatedCampaign = new Campaign();
        updatedCampaign.setCampaignFund(new BigDecimal("500.00"));

        Seller seller = new Seller();
        seller.setBalance(new BigDecimal("100.00"));
        existingCampaign.setSeller(seller);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(existingCampaign));

        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.updateCampaign(campaignId, updatedCampaign);
        });

        verify(campaignRepository, never()).save(any(Campaign.class));
    }

    @Test
    void testDeleteCampaign_Success() {
        Long campaignId = 1L;

        campaignService.deleteCampaign(campaignId);

        verify(campaignRepository, times(1)).deleteById(campaignId);
    }

    @Test
    void testGetCampaignById_Success() {
        Long campaignId = 1L;
        Campaign campaign = new Campaign();
        campaign.setId(campaignId);

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));

        Campaign result = campaignService.getCampaignById(campaignId);

        assertNotNull(result);
        assertEquals(campaignId, result.getId());
    }

    @Test
    void testGetCampaignById_NotFound() {
        Long campaignId = 1L;

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            campaignService.getCampaignById(campaignId);
        });
    }

}
