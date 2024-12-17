package com.challenge.store.service;

import com.challenge.store.DataTransferObjects.AddProduct;
import com.challenge.store.DataTransferObjects.RemoveProduct;
import com.challenge.store.entity.*;
import com.challenge.store.repository.CartRepository;
import com.challenge.store.repository.CustomerRepository;
import com.challenge.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public CartService (CartRepository cartRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        super();
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }


    public Cart getCartByCustomer(Long id) {
        Cart cart = cartRepository.findByCustomerId(id);
        return cartRepository.findById(cart.getId()).orElse(null);

    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id).get();

    }

    public Long getCustomerId(Long id) {
        Cart cart = cartRepository.findById(id).get();
        return cart.getCustomer().getId();
    }

    public Cart saveCart(Cart cart) {

        return cartRepository.save(cart);

    }

    public void emptyCart(Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                int newStock = product.getStock() + cartItem.getQuantity();
                product.setStock(newStock);
                productRepository.save(product);
            }
            cart.getCartItems().clear();
            cart.setTotalPrice(0.0);
            cartRepository.save(cart);
        }
    }

    public void updateCartTotalPrice(Cart cart) {
        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
    }

    public Cart addProductToCart(Long customerId, AddProduct addProduct) {

        Cart cart = cartRepository.findByCustomerId(customerId);

        if (cart == null) {

            cart = new Cart();


            Customer customer = customerRepository.findById(customerId).orElse(null);

            if (customer != null) {

                cart.setCustomer(customer);
                customer.setCart(cart);

            } else {

                return null;
            }
        }


        cart.setStatus(CartStatus.ACTIVE);


        Product product = productRepository.findById(addProduct.getProductId()).orElse(null);


        if (product != null && product.getStock() >= addProduct.getQuantity()) {

            CartItem existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(addProduct.getProductId()))
                    .findFirst()
                    .orElse(null);

            if (existingCartItem != null) {

                int newQuantity = existingCartItem.getQuantity() + addProduct.getQuantity();


                if (newQuantity <= product.getStock()) {
                    existingCartItem.setQuantity(newQuantity);
                    updateCartTotalPrice(cart);


                    product.setStock(product.getStock() - addProduct.getQuantity());
                    productRepository.save(product);

                    return cartRepository.save(cart);
                } else {

                    return null;
                }
            } else {

                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(addProduct.getQuantity());
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);

                updateCartTotalPrice(cart);


                product.setStock(product.getStock() - addProduct.getQuantity());
                productRepository.save(product);

                return cartRepository.save(cart);
            }
        }


        return null;
    }

    public Cart removeProductFromCart(Long customerId, RemoveProduct removeProduct) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        Product product = productRepository.findById(removeProduct.getProductId()).orElse(null);

        if (cart != null && product != null) {

            Optional<CartItem> cartItemOptional = Optional.empty();
            for (CartItem item : cart.getCartItems()) {
                if (item.getProduct().getId().equals(product.getId())) {
                    cartItemOptional = Optional.of(item);
                    break;
                }
            }

            if (cartItemOptional.isPresent()) {
                CartItem cartItem = cartItemOptional.get();
                int requestedQuantity = removeProduct.getQuantity();
                int availableQuantity = cartItem.getQuantity();

                if (requestedQuantity <= availableQuantity) {
                    int newQuantity = availableQuantity - requestedQuantity;

                    if (newQuantity > 0) {
                        cartItem.setQuantity(newQuantity);
                    } else {

                        newQuantity = 0;
                        cart.getCartItems().remove(cartItem);
                    }

                    updateCartTotalPrice(cart);


                    product.setStock(product.getStock() + requestedQuantity);



                    return cartRepository.save(cart);
                } else {

                }
            }
        }

        return null;
    }

}
