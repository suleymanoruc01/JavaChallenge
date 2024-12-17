package com.challenge.store.controller;

import com.challenge.store.entity.Customer;
import com.challenge.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/list-customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "list-customers";
    }


    @GetMapping("list-customers/{id}")
    public String getCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
        } else {
            model.addAttribute("message", "Müşteri bulunamadı.");
        }
        return "view-customer";
    }


    @GetMapping("list-customers/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "add-customer";
    }


    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, Model model) {
        Customer savedCustomer = customerService.addCustomer(customer);
        model.addAttribute("message", "Müşteri başarıyla eklendi.");
        return "redirect:/list-customers";
    }


    @GetMapping("/list-customers/edit/{id}")
    public String showeditCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "edit-customer";
        } else {
            model.addAttribute("message", "Müşteri bulunamadı.");
            return "redirect:/list-customers";
        }
    }


    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestParam String name) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            customer.setName(name);
            customerService.SaveCustomer(customer);
            return "redirect:/list-customers";
        } else {
            return "redirect:/list-customers";
        }
    }

    // Müşteri silme
    @GetMapping("/list-customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, Model model) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            model.addAttribute("message", "Müşteri başarıyla silindi.");
        } else {
            model.addAttribute("message", "Müşteri bulunamadı.");
        }
        return "redirect:/list-customers";
    }
}
