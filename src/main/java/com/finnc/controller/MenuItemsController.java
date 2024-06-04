package com.finnc.controller;

import com.finnc.models.MenuItem;
import com.finnc.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class MenuItemsController {

    private final StorageService storageService;

    @Autowired
    public MenuItemsController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMenuItem(@RequestBody MenuItem menuItem) {
        storageService.createMenuItem(menuItem);
        return ResponseEntity.ok("Menu item added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = storageService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }
}
