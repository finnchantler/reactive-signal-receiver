package com.finnc.model;

public record Order(Customer customer, MenuItem[] items, String deliverTo) {
}
