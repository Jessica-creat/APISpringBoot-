package org.example.apispringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apispringboot.model.Product;
import org.example.apispringboot.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@Import(ProductControllerTest.MockConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {

        @Bean
        @Primary // Priorité sur les autres beans de même type
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }
    }

    @Test
    void testGetAllProducts() throws Exception {
        when(service.getAll()).thenReturn(List.of(new Product(1L, "Product", 10.0, 5)));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        when(service.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() throws Exception {
        Product p = new Product(null, "Test", 100.0, 2);
        when(service.create(any(Product.class)))
                .thenReturn(new Product(1L, "Test", 100.0, 2));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
