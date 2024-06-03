package com.finnc.services;

import com.finnc.models.Order;
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

    public void storeOrder(Order order) {
        orderRepository.save(order);
        System.out.println("Stored order: " + order); // Debugging line
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

