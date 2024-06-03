package com.finnc.services;

import com.finnc.models.Orders;
import com.finnc.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private final OrderRepository orderRepository;

    @Autowired
    public StorageService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void storeOrder(Orders orders) {
        orderRepository.save(orders);
        System.out.println("Stored order: " + orders); // Debugging line
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}

