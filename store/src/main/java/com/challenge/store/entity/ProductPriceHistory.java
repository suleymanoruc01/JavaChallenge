package com.challenge.store.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    private LocalDateTime changeData;




    public ProductPriceHistory() {

    }

    public ProductPriceHistory(Product product, double price, int quantity, LocalDateTime changeData) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.changeData = changeData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getChangeData() {
        return changeData;
    }

    public void setChangeData(LocalDateTime changeData) {
        this.changeData = changeData;
    }


}
