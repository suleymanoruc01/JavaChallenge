package com.challenge.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "product")
public class Product extends Base{

    private String name;
    private double price;
    private int stock;
    private LocalDateTime createDate;

    public Product(String name, double price, int stock, LocalDateTime createDate) {
        super();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.createDate = createDate;
    }

    public Product() {

    }

    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }

    public LocalDateTime getCreateDate() {

        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return super.getId();
    }

}





