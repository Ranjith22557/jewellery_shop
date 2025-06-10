package com.rtech.jewellery.service;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private final ProductRepository productRepository;

    public PurchaseService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
         return productRepository.save(product);
    }

    public Optional<Product> findProduct(String type, String gram){

        return productRepository.findByTypeAndGram(type,gram);
    }

    public List<Product> getAllRemainingQtyProduct(){
        return productRepository.getAllRemainingQtyProduct();
    }

    public List<Product> getFindAll(){
        return productRepository.findAll();
    }
}
