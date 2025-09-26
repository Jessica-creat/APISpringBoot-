package org.example.apispringboot.service;

import org.example.apispringboot.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAll();
    Optional<Product> getById(Long id);
    Product create(Product product);
    Optional<Product> update(Long id, Product updatedProduct);
    boolean delete(Long id);
}
