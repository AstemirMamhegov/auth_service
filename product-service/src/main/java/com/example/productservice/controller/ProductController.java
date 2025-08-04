package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/filter/category")
    public List<Product> byCategory(@RequestParam String category) {
        return service.filterByCategory(category);
    }

    @GetMapping("/filter/price")
    public List<Product> byPrice(@RequestParam BigDecimal min,
                                 @RequestParam BigDecimal max) {
        return service.filterByPrice(min, max);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) {
        return service.search(q);
    }
}
