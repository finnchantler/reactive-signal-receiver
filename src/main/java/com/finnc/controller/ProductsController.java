package com.finnc.controller;

import com.finnc.models.Product;
import com.finnc.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ProductsController {

    private final StorageService storageService;

    @Autowired
    public ProductsController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMenuItem(@RequestBody Product product) {
        storageService.createMenuItem(product);
        return ResponseEntity.ok("Menu item added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllMenuItems() {
        List<Product> products = storageService.getAllMenuItems();
        return ResponseEntity.ok(products);
    }
}
