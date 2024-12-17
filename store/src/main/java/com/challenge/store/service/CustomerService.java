package com.challenge.store.service;

import com.challenge.store.entity.Customer;
import com.challenge.store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public void SaveCustomer(Customer customer){
        customerRepository.save(customer);
    }
    public Customer addCustomer(Customer customer) {

        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        return customerRepository.save(newCustomer);
    }

    public Customer getCustomerById(Long customerId) {

        return customerRepository.getCustomerById(customerId);

    }

    // Müşteriyi güncelle (ID'ye göre)
    public Customer updateCustomer(Long customerId, Customer customer) {
        Customer existingCustomer = customerRepository.getCustomerById(customerId);
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }


    // Müşteri silme işlemi
    public boolean deleteCustomer(Long customerId) {
        Customer existingCustomer = customerRepository.getCustomerById(customerId);
        if (existingCustomer != null) {
            customerRepository.delete(existingCustomer);
            return true;
        }
        return false;

    }








}
