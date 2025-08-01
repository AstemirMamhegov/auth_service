package com.example.productservice.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "products")
public class Product {

    @Id
    private UUID id;

    private String name;
    private String category;
    private Double price;
    private Boolean active;

    private Long createdAt;
    private Long updatedAt;
}
