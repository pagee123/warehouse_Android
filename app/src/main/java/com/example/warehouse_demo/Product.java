package com.example.warehouse_demo;

public class Product {
    private int serial_number;
    private String product_name;
    private String arrival_date;
    private  int quantity;

    public int getserial_number() {
        return serial_number;
    }

    public String getproduct_name() {
        return product_name;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public int getQuantity() {
        return quantity;
    }

    // 你可以根據需要添加更多字段
}
