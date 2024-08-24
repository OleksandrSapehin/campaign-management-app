package org.example.managing_campaigns_app_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellerDTO {

    private Long id;

    @NotBlank(message = "Seller username is mandatory")
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance must be positive")
    private BigDecimal balance;
}
