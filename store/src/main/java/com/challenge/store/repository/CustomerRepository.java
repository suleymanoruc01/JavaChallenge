package com.challenge.store.repository;

import com.challenge.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getCustomerById(Long customerId);
}
