package org.example.managing_campaigns_app_backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CampaignDTO {

    private Long id;

    @NotBlank(message = "Campaign name is mandatory")
    @Column(nullable = false)
    private String campaignName;

    @Size(min = 1, message = "At least one keyword is required")
    @Column(nullable = false)
    private List<String> keywords;

    @DecimalMin(value = "1.00", message = "Bid amount must be at least 1.00")
    @Column(nullable = false)
    private BigDecimal bidAmount;

    @DecimalMin(value = "0.01", message = "Campaign fund must be greater than 0")
    @Column(nullable = false)
    private BigDecimal campaignFund;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Radius must be at least 1 kilometer")
    private Integer radius;

    @NotNull(message = "Status is mandatory")
    private Boolean status;

    @NotBlank(message = "Town is mandatory")
    private String town;

}
