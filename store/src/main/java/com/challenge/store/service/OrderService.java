package com.challenge.store.service;


import com.challenge.store.entity.*;
import com.challenge.store.repository.CartRepository;
import com.challenge.store.repository.CustomerRepository;
import com.challenge.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;


    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final CartService cartService;

    @Autowired
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CartService cartService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.customerRepository = customerRepository;
    }


    public Order placeOrder(Long cartId) {
        Cart cart = cartRepository.getById(cartId);

        if (cart != null) {
            Order order = new Order();
            order.setCustomer(cart.getCustomer());

            List<CartItem> cartItems = cart.getCartItems();
            List<OrderItem> orderItems = new ArrayList<>();
            double totalOrderPrice = 0.0;

            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(cartItem.getProduct().getName());
                orderItem.setQuantity(cartItem.getQuantity());
                cartItem.getProduct().setStock(cartItem.getProduct().getStock()-cartItem.getQuantity());
                double itemTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
                orderItem.setPriceAtOrder(itemTotalPrice);

                totalOrderPrice += itemTotalPrice;
                orderItems.add(orderItem);
            }

            order.setItems(orderItems);
            order.setTotalPrice(totalOrderPrice);


            Order savedOrder = orderRepository.save(order);




            cartService.emptyCart(cartId);
            cart.setStatus(CartStatus.COMPLETED);
            cartRepository.save(cart);


            return savedOrder;
        }
        return null;
    }



    public Order getOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        return optionalOrder.orElse(null);

    }

    public List<Order> getOrdersByCustomerId(Long customerId) {

        return orderRepository.findByCustomer_Id(customerId);

    }





}
