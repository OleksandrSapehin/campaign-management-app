package org.example.managing_campaigns_app_backend.services;

import org.example.managing_campaigns_app_backend.dto.CampaignDTO;
import org.example.managing_campaigns_app_backend.models.Campaign;

import java.util.List;

public interface CampaignService {

    Campaign createCampaign(Campaign campaign, Long productId, Long sellerId);
    List<Campaign> getCampaignsForProduct(Long productId);
    Campaign updateCampaign(Long campaignId, Campaign campaign);
    void deleteCampaign(Long campaignId);
    Campaign getCampaignById(Long campaignId);
}
