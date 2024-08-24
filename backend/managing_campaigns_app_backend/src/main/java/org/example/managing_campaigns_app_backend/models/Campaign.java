package org.example.managing_campaigns_app_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campaignName;

    @ElementCollection
    private List<String> keywords;

    @NotNull(message = "Bid amount is mandatory")
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    private BigDecimal campaignFund;

    @Column(nullable = false)
    private Boolean status;

    private String town;

    @Column(nullable = false)
    private Integer radius;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

}
