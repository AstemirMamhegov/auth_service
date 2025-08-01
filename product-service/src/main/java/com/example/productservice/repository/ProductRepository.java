package com.example.productservice.repository;

import com.example.productservice.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, UUID> {
    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
}
