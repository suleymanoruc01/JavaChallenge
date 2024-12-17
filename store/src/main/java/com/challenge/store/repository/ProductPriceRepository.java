package com.challenge.store.repository;

import com.challenge.store.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPriceHistory, Long> {

    ProductPriceHistory findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}
