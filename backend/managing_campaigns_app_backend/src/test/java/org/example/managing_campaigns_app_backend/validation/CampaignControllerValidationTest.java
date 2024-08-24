package org.example.managing_campaigns_app_backend.validation;

import org.example.managing_campaigns_app_backend.controllers.SellerController;
import org.example.managing_campaigns_app_backend.models.Product;
import org.example.managing_campaigns_app_backend.models.Seller;
import org.example.managing_campaigns_app_backend.repository.ProductRepository;
import org.example.managing_campaigns_app_backend.repository.SellerRepository;
import org.example.managing_campaigns_app_backend.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class CampaignControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    private Seller seller;
    private Product product;

    @BeforeEach
    @Transactional
    void setUp() {
        seller = new Seller();
        seller.setUsername("validSeller");
        seller.setEmail("valid@example.com");
        seller.setBalance(new BigDecimal("1000.00"));
        sellerRepository.save(seller);

        product = new Product();
        product.setName("Valid Product");
        productRepository.save(product);
    }

    @Test
    void whenCreateCampaignWithInvalidData_thenBadRequest() throws Exception {
        String invalidCampaignJson = """
                {
                    "campaignName": "",
                    "bidAmount": "0",
                    "campaignFund": "-10",
                    "status": null,
                    "town": "",
                    "radius": null
                }
                """;

        mockMvc.perform(post("/api/campaigns/products/1?sellerId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidCampaignJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campaignName").value("Campaign name is mandatory"))
                .andExpect(jsonPath("$.bidAmount").value("Bid amount must be at least 1.00"))
                .andExpect(jsonPath("$.campaignFund").value("Campaign fund must be greater than 0"))
                .andExpect(jsonPath("$.status").value("Status is mandatory"))
                .andExpect(jsonPath("$.town").value("Town is mandatory"))
                .andExpect(jsonPath("$.radius").value("Radius is mandatory"));

    }

    @Test
    @Transactional
    void whenCreateCampaignWithValidData_thenSuccess() throws Exception {
        String validCampaignJson = """
                {
                    "campaignName": "Valid Campaign",
                    "keywords": ["keyword1", "keyword2"],
                    "bidAmount": "10.00",
                    "campaignFund": "100.00",
                    "status": true,
                    "town": "Sample Town",
                    "radius": 10
                }
                """;

        mockMvc.perform(post("/api/campaigns/products/" + product.getId() + "?sellerId=" + seller.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validCampaignJson))
                .andExpect(status().isOk());
    }
}
