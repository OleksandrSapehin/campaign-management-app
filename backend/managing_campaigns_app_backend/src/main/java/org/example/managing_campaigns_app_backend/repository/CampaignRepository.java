package org.example.managing_campaigns_app_backend.repository;

import org.example.managing_campaigns_app_backend.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign,Long> {
    List<Campaign> findByProductId(Long productId);
}
