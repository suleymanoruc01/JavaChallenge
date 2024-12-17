package com.challenge.store.controller;

import com.challenge.store.DataTransferObjects.CreateProduct;
import com.challenge.store.entity.Product;
import com.challenge.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/list")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/list";
    }


    @GetMapping("list/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new CreateProduct());
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute CreateProduct createProduct) {
        productService.createProduct(createProduct);
        return "redirect:/list";
    }


    @GetMapping("list/ed/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "edit";
        }
        return "redirect:/list";
    }

    @PostMapping("/ed/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute CreateProduct updatedProduct) {
        productService.updateProduct(id, updatedProduct);
        return "redirect:/list";
    }


    @GetMapping("/list/del/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/list";
    }

}
