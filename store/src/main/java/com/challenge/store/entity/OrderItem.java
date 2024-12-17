package com.challenge.store.entity;


import jakarta.persistence.Entity;

@Entity
public class OrderItem extends Base{


    private String productname;

    private int quantity;
    private double priceAtOrder;

    public String getProductName() {
        return productname;
    }
    public void setProductName(String productname) {
        this.productname = productname;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPriceAtOrder() {
        return priceAtOrder;
    }
    public void setPriceAtOrder(double priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

}
