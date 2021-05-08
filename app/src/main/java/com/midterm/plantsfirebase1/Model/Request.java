package com.midterm.plantsfirebase1.Model;

import java.util.List;

public class Request {
    private String name;
    private String phone;
    private String address;
    private String total;
    private String status;
    private List<Order> plantOrder;

    public Request() {
    }

    public Request(String name, String phone, String address, String total, List<Order> plantOrder) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.plantOrder = plantOrder;
        this.status = "0"; //Default is 0; 0 is Ordered, 1 is Shipping, 2 is Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getPlantOrder() {
        return plantOrder;
    }

    public void setPlantOrder(List<Order> plantOrder) {
        this.plantOrder = plantOrder;
    }
}
