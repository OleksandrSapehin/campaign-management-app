package org.example.managing_campaigns_app_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String name;
}
