package com.example.productservice.service;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Optional<Product> getById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<Product> filterByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public List<Product> filterByPrice(BigDecimal min, BigDecimal max) {
        return repository.findByPriceBetween(min, max);
    }

    public List<Product> search(String text) {
        return repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text);
    }
}
