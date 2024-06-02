package com.finnc.models;

import java.util.Arrays;

public record Order(Customer customer, String[] items, String deliverTo) {
    @Override
    public String toString() {
        return "ORDER: " + customer + " - ITEMS: " + Arrays.toString(items) + " - DELIVER TO: " + deliverTo;
    }
}