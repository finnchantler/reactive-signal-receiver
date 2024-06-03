package com.finnc.controller;

import com.finnc.models.Orders;
import com.finnc.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final StorageService storageService;

    @Autowired
    public OrdersController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/store")
    public ResponseEntity<String> storeOrder(@RequestBody Orders orders) {
        storageService.storeOrder(orders);
        return ResponseEntity.ok("Order stored successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = storageService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
