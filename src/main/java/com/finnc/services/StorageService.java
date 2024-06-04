package com.finnc.services;

import com.finnc.models.Customer;
import com.finnc.models.Product;
import com.finnc.models.Orders;
import com.finnc.repo.CustomerRepository;
import com.finnc.repo.ProductRepository;
import com.finnc.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public StorageService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public String storeOrder(Orders orders) {
        orderRepository.save(orders);
        return "Stored order: " + orders;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public String storeCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Stored customer: " + customer;
    }

    public List<Customer> searchCustomersByName(String name) { return customerRepository.findByNameContaining(name); }

    public String createMenuItem(Product product) {
        productRepository.save(product);
        return "Stored menu item: " + product;
    }

    public List<Product> getAllMenuItems() { return productRepository.findAll(); }
}

