package org.example.managing_campaigns_app_backend.mappers;

import org.example.managing_campaigns_app_backend.dto.SellerDTO;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper extends Mappable<Seller, SellerDTO>{
    @Override
    SellerDTO toDto(Seller entity);

    @Override
    List<SellerDTO> toDto(List<Seller> entity);

    @Override
    Seller toEntity(SellerDTO dto);
}
