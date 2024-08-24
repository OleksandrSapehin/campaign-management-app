package org.example.managing_campaigns_app_backend.mappers;

import org.example.managing_campaigns_app_backend.dto.ProductDTO;
import org.example.managing_campaigns_app_backend.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Mappable<Product, ProductDTO>{
    @Override
    ProductDTO toDto(Product entity);

    @Override
    List<ProductDTO> toDto(List<Product> entity);

    @Override
    Product toEntity(ProductDTO dto);
}
