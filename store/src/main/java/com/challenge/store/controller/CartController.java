package com.challenge.store.controller;


import com.challenge.store.DataTransferObjects.AddProduct;
import com.challenge.store.DataTransferObjects.RemoveProduct;
import com.challenge.store.entity.Cart;
import com.challenge.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("cart/{id}")
    public String getCart(@PathVariable Long id, Model model) {
        Cart cart = cartService.getCartByCustomer(id);
        if (cart != null) {
            model.addAttribute("cart", cart);
            model.addAttribute("cartItems", cart.getCartItems());
        } else {
            model.addAttribute("message", "Sepet bulunamadı.");
        }
        return "view-cart";
    }


    @GetMapping("cart/add/{id}")
    public String showAddProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("addProductRequest", new AddProduct());
        return "add-product";
    }


    @PostMapping("/add/{id}")
    public String addProductToCart(@PathVariable Long id,
                                   @ModelAttribute AddProduct addProduct,
                                   Model model) {
        Cart updatedCart = cartService.addProductToCart(id, addProduct);
        if (updatedCart != null) {
            model.addAttribute("cart", updatedCart);
            model.addAttribute("message", "Ürün başarıyla sepete eklendi.");
        } else {
            model.addAttribute("message", "Ürün sepete eklenemedi. Stok kontrol ediniz.");
        }
        return "redirect:/cart/" + id;
    }


    @GetMapping("cart/remove/{customerId}")
    public String showRemoveProductForm(@PathVariable Long customerId, Model model) {
        model.addAttribute("customerId", customerId);
        model.addAttribute("removeProductRequest", new RemoveProduct());
        return "remove-product";
    }


    @PostMapping("/remove/{customerId}")
    public String removeProductFromCart(@PathVariable Long customerId,
                                        @ModelAttribute RemoveProduct removeProduct,
                                        Model model) {
        Cart updatedCart = cartService.removeProductFromCart(customerId, removeProduct);
        if (updatedCart != null) {
            model.addAttribute("cart", updatedCart);
            model.addAttribute("message", "Ürün başarıyla sepetten çıkarıldı.");
        } else {
            model.addAttribute("message", "Ürün sepetten çıkarılamadı.");
        }
        return "redirect:/cart/" + customerId;
    }


    @PostMapping("/{customerId}/empt")
    public String emptyCart(@PathVariable Long customerId, Model model) {
        cartService.emptyCart(customerId);
        Long id = cartService.getCustomerId(customerId);
        model.addAttribute("message", "Sepet başarıyla boşaltıldı.");
        return "redirect:/cart/" + id;
    }
}
