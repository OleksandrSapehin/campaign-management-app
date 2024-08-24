package org.example.managing_campaigns_app_backend.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreateSellerWithInvalidData_thenBadRequest() throws Exception {
        String invalidSellerJson = """
                {
                    "username": "",
                    "email": "invalid-email",
                    "balance": -1
                }
                """;

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidSellerJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Seller username is mandatory"))
                .andExpect(jsonPath("$.email").value("Email should be valid"))
                .andExpect(jsonPath("$.balance").value("Balance must be positive"));
    }

    @Test
    void whenCreateSellerWithValidData_thenSuccess() throws Exception {
        String validSellerJson = """
                {
                    "username": "ValidSeller",
                    "email": "valid@example.com",
                    "balance": 100.00
                }
                """;

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validSellerJson))
                .andExpect(status().isOk());
    }

}
