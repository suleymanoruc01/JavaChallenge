package com.challenge.store.controller;


import com.challenge.store.entity.Cart;
import com.challenge.store.entity.Order;
import com.challenge.store.service.CartService;
import com.challenge.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;


    @PostMapping("/place/{cartId}")
    public String placeOrder(@PathVariable Long cartId, Model model) {
        Cart cart = cartService.getCart(cartId);
        if(cart.getTotalPrice()!=0) {
            Order order = orderService.placeOrder(cartId);
            if (order != null) {
                model.addAttribute("message", "Sipariş başarıyla oluşturuldu!");
                model.addAttribute("order", order);
                return "order-success";
            }
        }
        model.addAttribute("message", "Sipariş oluşturulurken bir hata oldu! Lütfen tekrar deneyiniz.");
        return "order-fail";
    }


    @GetMapping("/order/{orderId}")
    public String viewOrder(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrder(orderId);
        if (order != null) {
            model.addAttribute("order", order);
            return "view-order";
        }
        model.addAttribute("message", "Sipariş bulunamadı.");
        return "order-fail";
    }


    @GetMapping("/order/customer/{customerId}")
    public String getOrdersByCustomer(@PathVariable Long customerId, Model model) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        if (!orders.isEmpty()) {
            model.addAttribute("orders", orders);
            return "list-orders";
        }
        model.addAttribute("message", "Bu müşteri için sipariş bulunmamaktadır.");
        return "order-fail";
    }
}
