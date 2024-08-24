package org.example.managing_campaigns_app_backend.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCreateProductWithInvalidData_thenBadRequest() throws Exception {
        String invalidProductJson = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Product name is mandatory"));
    }

    @Test
    void whenCreateProductWithValidData_thenSuccess() throws Exception {
        String validProductJson = """
                {
                    "name": "Valid Product"
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validProductJson))
                .andExpect(status().isOk());
    }

}
