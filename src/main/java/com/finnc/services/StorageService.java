package com.finnc.services;

import com.finnc.models.Customer;
import com.finnc.models.Orders;
import com.finnc.repo.CustomerRepository;
import com.finnc.repo.MenuItemRepository;
import com.finnc.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public StorageService(OrderRepository orderRepository, MenuItemRepository menuItemRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.customerRepository = customerRepository;
    }

    public String storeOrder(Orders orders) {
        orderRepository.save(orders);
        return "Stored order: " + orders; // Debugging line
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public String storeCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Stored customer: " + customer;
    }

    public List<Customer> searchCustomersByName(String name) { return customerRepository.findByNameContaining(name); }
}

