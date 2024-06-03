package com.finnc.controller;

import com.finnc.models.Order;
import com.finnc.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class SpringReceiverController {

    private final StorageService storageService;

    @Autowired
    public SpringReceiverController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/store")
    public ResponseEntity<String> storeOrder(@RequestBody Order order) {
        storageService.storeOrder(order);
        return ResponseEntity.ok("Order stored successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = storageService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
