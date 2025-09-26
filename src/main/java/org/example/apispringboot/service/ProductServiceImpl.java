package org.example.apispringboot.service;

import org.example.apispringboot.model.Product;
import org.example.apispringboot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    public Product create(Product product) {
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product updatedProduct) {
        return repo.findById(id).map(p -> {
            p.setName(updatedProduct.getName());
            p.setPrice(updatedProduct.getPrice());
            p.setQuantity(updatedProduct.getQuantity());
            return repo.save(p);
        });
    }

    public boolean delete(Long id) {
        return repo.findById(id).map(p -> {
            repo.delete(p);
            return true;
        }).orElse(false);
    }
}
