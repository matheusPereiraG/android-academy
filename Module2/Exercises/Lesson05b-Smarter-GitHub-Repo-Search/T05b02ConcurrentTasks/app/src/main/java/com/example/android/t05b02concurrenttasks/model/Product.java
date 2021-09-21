package com.example.android.t05b02concurrenttasks.model;

import androidx.annotation.NonNull;

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hello " + this.name + " " + this.price;
    }
}
