package com.rtech.jewellery.service;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    SalesRepository salesRepository;

    //get price based on type and gram
    public Object getPriceByTypeAndGram(String type,String gram){
        return salesRepository.findPriceByTypeAndGram(type,gram);
    }

    //Save customer
    public Sales saveCustomer(Sales sales){
       return salesRepository.save(sales);
    }

    //update quantity
    public Product findProduct(String type, String gram){
        return salesRepository.findProduct(type,gram);
    }

    //get all Sales List
    public List<Sales> findAll(){
        return salesRepository.findAll();
    }
}
