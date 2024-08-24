package org.example.managing_campaigns_app_backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.managing_campaigns_app_backend.dto.CampaignDTO;
import org.example.managing_campaigns_app_backend.mappers.CampaignMapper;
import org.example.managing_campaigns_app_backend.models.Campaign;
import org.example.managing_campaigns_app_backend.services.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
@Validated
public class CampaignController {

    private final CampaignService campaignService;
    private final CampaignMapper campaignMapper;

    @PostMapping("/products/{productId}")
    public ResponseEntity<CampaignDTO> createCampaign(
            @PathVariable Long productId,
            @RequestParam Long sellerId,
            @Valid @RequestBody CampaignDTO campaignDTO) {
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        Campaign createdCampaign = campaignService.createCampaign(campaign, productId, sellerId);
        return ResponseEntity.ok(campaignMapper.toDto(createdCampaign));
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<CampaignDTO> getCampaignById(@PathVariable Long campaignId) {
        Campaign campaign = campaignService.getCampaignById(campaignId);
        CampaignDTO campaignDTO = campaignMapper.toDto(campaign);
        return ResponseEntity.ok(campaignDTO);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<CampaignDTO>> getCampaignsForProduct(@PathVariable Long productId) {
        List<Campaign> campaigns = campaignService.getCampaignsForProduct(productId);
        List<CampaignDTO> campaignDTOs = campaignMapper.toDto(campaigns);
        return ResponseEntity.ok(campaignDTOs);
    }

    @PutMapping("/{campaignId}")
    public ResponseEntity<CampaignDTO> updateCampaign(
            @PathVariable Long campaignId,
            @Valid @RequestBody CampaignDTO campaignDTO) {
        Campaign campaign = campaignMapper.toEntity(campaignDTO);
        Campaign updatedCampaign = campaignService.updateCampaign(campaignId, campaign);
        return ResponseEntity.ok(campaignMapper.toDto(updatedCampaign));
    }

    @DeleteMapping("/{campaignId}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Long campaignId) {
        campaignService.deleteCampaign(campaignId);
        return ResponseEntity.ok("Campaign deleted");
    }

}
