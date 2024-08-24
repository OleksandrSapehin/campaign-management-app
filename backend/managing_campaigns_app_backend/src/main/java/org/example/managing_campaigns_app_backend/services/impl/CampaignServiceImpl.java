package org.example.managing_campaigns_app_backend.services.impl;

import org.example.managing_campaigns_app_backend.models.Campaign;
import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.mappers.CampaignMapper;
import org.example.managing_campaigns_app_backend.repository.CampaignRepository;
import org.example.managing_campaigns_app_backend.services.CampaignService;
import org.example.managing_campaigns_app_backend.services.ProductService;
import org.example.managing_campaigns_app_backend.services.SellerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductService productService;
    private final SellerService sellerService;
    private final CampaignMapper campaignMapper;


    @Override
    @Transactional
    public Campaign createCampaign(Campaign campaign, Long productId, Long sellerId) {
        Product product = productService.getProductById(productId);
        Seller seller = sellerService.getSellerById(sellerId);

        if (seller.getBalance().compareTo(campaign.getCampaignFund()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        campaign.setProduct(product);
        campaign.setSeller(seller);

        sellerService.updateSellerBalance(seller, campaign.getCampaignFund());

        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getCampaignsForProduct(Long productId) {
        return campaignRepository.findByProductId(productId);
    }

    @Override
    @Transactional
    public Campaign updateCampaign(Long campaignId, Campaign campaign) {
        Campaign existingCampaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        Seller seller = existingCampaign.getSeller();

        BigDecimal difference = campaign.getCampaignFund().subtract(existingCampaign.getCampaignFund());

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            if (seller.getBalance().compareTo(difference) < 0) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            sellerService.updateSellerBalance(seller, difference);
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            seller.setBalance(seller.getBalance().subtract(difference));
        }


        existingCampaign.setCampaignName(campaign.getCampaignName());
        existingCampaign.setKeywords(campaign.getKeywords());
        existingCampaign.setBidAmount(campaign.getBidAmount());
        existingCampaign.setCampaignFund(campaign.getCampaignFund());
        existingCampaign.setStatus(campaign.getStatus());
        existingCampaign.setTown(campaign.getTown());
        existingCampaign.setRadius(campaign.getRadius());

        return campaignRepository.save(existingCampaign);
    }

    @Override
    @Transactional
    public void deleteCampaign(Long campaignId) {
        campaignRepository.deleteById(campaignId);
    }

    @Override
    public Campaign getCampaignById(Long campaignId) {
        return campaignRepository.findById(campaignId).orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
    }
}
