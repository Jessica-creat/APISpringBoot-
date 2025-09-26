package org.example.apispringboot.service;

import org.example.apispringboot.model.Product;
import org.example.apispringboot.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void testGetAll() {
        when(repo.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        assertEquals(2, service.getAll().size());
    }

    @Test
    void testGetByIdFound() {
        Product p = new Product(1L, "Phone", 200.0, 10);
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        assertTrue(service.getById(1L).isPresent());
    }

    @Test
    void testUpdateNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertTrue(service.update(1L, new Product()).isEmpty());
    }
}
