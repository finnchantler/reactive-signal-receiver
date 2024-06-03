package com.finnc.models;

import jakarta.persistence.*;
import java.util.Arrays;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliverTo;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ElementCollection
    private String[] items;

    public Orders() {}

    public Orders(Customer customer, String[] items, String deliverTo) {
        this.customer = customer;
        this.items = items;
        this.deliverTo = deliverTo;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ORDER ID " + getId() + " : " + getCustomer() + " - ITEMS: " + Arrays.toString(getItems()) + " - DELIVER TO: " + getDeliverTo();
    }
}
