package com.challenge.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends Base {

    private String Name;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;




    public Customer (String Name){
        this.Name = Name;

    }

    public Customer(){

    }

    public void setName(String Name){
        this.Name = Name;
    }

    public String getName(){
        return Name;
    }

    public void setCart(Cart cart) {

        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Long getId(){
        return super.getId();
    }











}



