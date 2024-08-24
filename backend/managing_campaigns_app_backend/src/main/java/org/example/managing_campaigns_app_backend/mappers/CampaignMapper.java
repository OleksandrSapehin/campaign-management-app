package org.example.managing_campaigns_app_backend.mappers;

import org.example.managing_campaigns_app_backend.dto.CampaignDTO;
import org.example.managing_campaigns_app_backend.models.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampaignMapper extends Mappable<Campaign, CampaignDTO> {

    @Override
    CampaignDTO toDto(Campaign entity);

    @Override
    List<CampaignDTO> toDto(List<Campaign> entity);

    @Override
    Campaign toEntity(CampaignDTO dto);

}
