package com.challenge.store.service;

import com.challenge.store.DataTransferObjects.CreateProduct;
import com.challenge.store.entity.Product;

import com.challenge.store.entity.ProductPriceHistory;
import com.challenge.store.repository.CustomerRepository;
import com.challenge.store.repository.ProductPriceRepository;
import com.challenge.store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductPriceRepository productPriceHistoryRepository;
    @Autowired
    private ProductPriceRepository productPriceRepository;

    public ProductService(ProductRepository productRepository, ProductPriceRepository productPriceHistoryRepository) {

        super();
        this.productRepository = productRepository;
        this.productPriceHistoryRepository = productPriceHistoryRepository;
    }
    public Product createProduct(CreateProduct createProduct) {

        Product product = new Product();
        product.setName(createProduct.getName());
        product.setPrice(createProduct.getPrice());
        product.setStock(createProduct.getStock());
        product.setCreateDate(LocalDateTime.now());
        return productRepository.save(product);

    }

    public void SaveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductHist(Long product_id){
        Long count = productPriceHistoryRepository.count();
        while(productPriceHistoryRepository.count() != 0.0) {
            if(productPriceHistoryRepository.existsByProductId(product_id)){
                Long id = productPriceHistoryRepository.findByProductId(product_id).getId();
                productPriceHistoryRepository.deleteById(id);
            }

        }
        System.out.println("Deleted product with id: " + product_id);
    }



    public Product updateProduct(Long productId, CreateProduct updatedProduct) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("not found: " + productId));


        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());


        Product savedProduct = productRepository.save(existingProduct);

        ProductPriceHistory priceHistory = new ProductPriceHistory();

        if(productPriceHistoryRepository.existsByProductId(productId)) {
            priceHistory = productPriceHistoryRepository.findByProductId(productId);

        }


        priceHistory.setProduct(savedProduct);
        priceHistory.setPrice(updatedProduct.getPrice());
        priceHistory.setQuantity(updatedProduct.getStock());
        priceHistory.setChangeData(LocalDateTime.now());


        productPriceHistoryRepository.save(priceHistory);


        return savedProduct;
    }

    public void deleteProduct(Long productId) {

        if(productPriceHistoryRepository.count() != 0){
            deleteProductHist(productId);
        }

        productRepository.deleteById(productId);


    }

    public Product getProduct(Long productId) {


        return productRepository.findById(productId).orElse(null);

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}






